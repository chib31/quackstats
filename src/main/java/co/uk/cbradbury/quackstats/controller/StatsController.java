package co.uk.cbradbury.quackstats.controller;

import co.uk.cbradbury.quackstats.exception.NotFoundException;
import co.uk.cbradbury.quackstats.service.StatsService;
import co.uk.cbradbury.quackstats.service.TeamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.UUID;

@RestController
public class StatsController {

    private final StatsService statsService;

    private final TeamService teamService;

    public StatsController(StatsService statsService, TeamService teamService) {
        this.statsService = statsService;
        this.teamService = teamService;
    }

    @GetMapping("/stats/{teamId}/batting")
    public ResponseEntity<?> fetchBattingStats(@PathVariable UUID teamId) {
        var team = teamService.fetchTeamById(teamId).orElseThrow(
                () -> new NotFoundException(String.format("No team with ID %s", teamId)));

        var responseBody = new HashMap<>();
        responseBody.put("stats", statsService.fetchBattingStats(team));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/stats/{teamId}/bowling")
    public ResponseEntity<?> fetchBowlingStats(@PathVariable UUID teamId) {
        var team = teamService.fetchTeamById(teamId).orElseThrow(
                () -> new NotFoundException(String.format("No team with ID %s", teamId)));

        var responseBody = new HashMap<>();
        responseBody.put("stats", statsService.fetchBowlingStats(team));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/stats/{teamId}/fielding")
    public ResponseEntity<?> fetchFieldingStats(@PathVariable UUID teamId) {
        var team = teamService.fetchTeamById(teamId).orElseThrow(
                () -> new NotFoundException(String.format("No team with ID %s", teamId)));

        var responseBody = new HashMap<>();
        responseBody.put("stats", statsService.fetchFieldingStats(team));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }
}
