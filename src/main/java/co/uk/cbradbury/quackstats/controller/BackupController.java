package co.uk.cbradbury.quackstats.controller;

import co.uk.cbradbury.quackstats.exception.BadRequestException;
import co.uk.cbradbury.quackstats.exception.NotFoundException;
import co.uk.cbradbury.quackstats.json.backup.ScorecardJson;
import co.uk.cbradbury.quackstats.service.BackupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/scorecard-backup")
public class BackupController {

    private final BackupService backupService;

    public BackupController(BackupService backupService) {
        this.backupService = backupService;
    }

    @Transactional
    @PutMapping("/save")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> createFromBackup(@Valid @RequestBody List<ScorecardJson> scorecardsJson) {

        for (ScorecardJson scorecardJson : scorecardsJson) {
            var existingScorecard = backupService.fetchScorecardByDateAndOrder(scorecardJson.getDate(),
                    scorecardJson.getFixtureOrder());
            if (existingScorecard.isPresent()) {
                throw new BadRequestException(String.format(
                        "Scorecard already exists on date %s with fixture order %s", scorecardJson.getDate(),
                        scorecardJson.getFixtureOrder()));
            }

            var teamId = scorecardJson.getTeamId();
            var team = backupService.findTeamById(teamId).orElseThrow(
                    () -> new NotFoundException(String.format("No team with ID %s", teamId)));

            var opponentName = scorecardJson.getOpponentName();
            var opponent = backupService.findTeamByApproximateName(opponentName).orElseThrow(
                    () -> new NotFoundException(String.format("No team with name %s", opponentName)));

            // Verification only
            for (var squadMemberJson : scorecardJson.getSquadMemberList()) {
                backupService.findPlayerByNameAndMainTeam(squadMemberJson.getName(), team).orElseThrow(
                        () -> new NotFoundException(String.format("No player with name %s in team %s",
                                squadMemberJson.getName(), team.getName())));
            }

            backupService.createFromBackup(scorecardJson, team, opponent);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/retrieve/{scorecardId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> retrieveBackup(@PathVariable Long scorecardId) {
        var scorecard = backupService.findScorecardById(scorecardId).orElseThrow(
                () -> new NotFoundException(String.format("No scorecard with ID %s", scorecardId)));

        var fullScorecard = backupService.fetchScorecardJson(scorecard);

        return new ResponseEntity<>(fullScorecard, HttpStatus.OK);
    }

    @GetMapping("/retrieve-all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> retrieveAllBackups() {
        var scorecards = backupService.fetchAllScorecardsJson();
        return new ResponseEntity<>(scorecards, HttpStatus.OK);
    }
}
