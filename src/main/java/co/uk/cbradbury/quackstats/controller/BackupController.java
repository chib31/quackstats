package co.uk.cbradbury.quackstats.controller;

import co.uk.cbradbury.quackstats.exception.BadRequestException;
import co.uk.cbradbury.quackstats.exception.NotFoundException;
import co.uk.cbradbury.quackstats.json.ScorecardJson;
import co.uk.cbradbury.quackstats.service.BackupService;
import co.uk.cbradbury.quackstats.service.TeamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/backup")
public class BackupController {

    private final BackupService backupService;

    private final TeamService teamService;

    public BackupController(BackupService backupService, TeamService teamService) {
        this.backupService = backupService;
        this.teamService = teamService;
    }

    @Transactional
    @PutMapping("/import-scorecards")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> importScorecards(@Valid @RequestBody List<ScorecardJson> scorecardsJson) {

        for (ScorecardJson scorecardJson : scorecardsJson) {
            var existingScorecard = backupService.fetchScorecardByDateAndOrder(scorecardJson.getDate(),
                    scorecardJson.getFixtureOrder());
            if (existingScorecard.isPresent()) {
                throw new BadRequestException(String.format(
                        "Scorecard already exists on date %s with fixture order %s", scorecardJson.getDate(),
                        scorecardJson.getFixtureOrder()));
            }

            var teamId = scorecardJson.getTeamId();
            var team = teamService.fetchTeamById(teamId).orElseThrow(
                    () -> new NotFoundException(String.format("No team with ID %s", teamId)));

            var opponentName = scorecardJson.getOpponentName();
            var opponent = teamService.fetchTeamByApproximateName(opponentName).orElseThrow(
                    () -> new NotFoundException(String.format("No team with name %s", opponentName)));

            // Verification only
            for (var squadMemberJson : scorecardJson.getSquadMemberList()) {
                teamService.fetchPlayerByNameAndMainTeam(squadMemberJson.getName(), team).orElseThrow(
                        () -> new NotFoundException(String.format("No player with name %s in team %s",
                                squadMemberJson.getName(), team.getName())));
            }

            backupService.importScorecard(scorecardJson, team, opponent);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/export-scorecard/{scorecardId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> exportScorecard(@PathVariable String scorecardId) {
        var scorecard = backupService.findScorecardById(UUID.fromString(scorecardId)).orElseThrow(
                () -> new NotFoundException(String.format("No scorecard with ID %s", scorecardId)));

        var fullScorecard = backupService.constructScorecardJson(scorecard);

        return new ResponseEntity<>(fullScorecard, HttpStatus.OK);
    }

    @GetMapping("/export-all-scorecards")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> exportAllScorecards() {
        return new ResponseEntity<>(backupService.constructAllScorecardsJson(), HttpStatus.OK);
    }
}
