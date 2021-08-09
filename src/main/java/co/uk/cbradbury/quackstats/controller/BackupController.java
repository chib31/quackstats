package co.uk.cbradbury.quackstats.controller;

import co.uk.cbradbury.quackstats.exception.NotFoundException;
import co.uk.cbradbury.quackstats.service.BackupService;
import co.uk.cbradbury.quackstats.service.ScorecardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/backup")
public class BackupController {

    private final BackupService backupService;

    private final ScorecardService scorecardService;

    public BackupController(BackupService backupService, ScorecardService scorecardService) {
        this.backupService = backupService;
        this.scorecardService = scorecardService;
    }

    @GetMapping("/scorecard/{scorecardId}")
    public ResponseEntity<?> getScorecardJson(@PathVariable Long scorecardId) {
        var scorecard = scorecardService.findScorecardById(scorecardId).orElseThrow(
                () -> new NotFoundException(String.format("No scorecard with ID %s", scorecardId)));

        var fullScorecard = backupService.getFullScorecard(scorecard);
        return new ResponseEntity<>(fullScorecard, HttpStatus.OK);
    }
}
