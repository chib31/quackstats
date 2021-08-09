package co.uk.cbradbury.quackstats.controller;

import co.uk.cbradbury.quackstats.exception.NotFoundException;
import co.uk.cbradbury.quackstats.json.PlayerJson;
import co.uk.cbradbury.quackstats.service.StatsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@CrossOrigin
@RestController
public class StatsController {

    private final StatsService statsService;

    public StatsController(StatsService statsService) {
        this.statsService = statsService;
    }

    @PutMapping("/add-team")
    public ResponseEntity<?> addTeam(@RequestBody String teamName) {
        if (statsService.teamNameExists(teamName)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            statsService.addTeam(teamName);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }

    @PutMapping("/add-player")
    public ResponseEntity<?> addPlayer(@RequestBody PlayerJson newPlayerJson) {
        var teamId = newPlayerJson.getTeamId();
        var team = statsService.getTeamFromId(teamId).orElseThrow(
                () -> new NotFoundException(String.format("No team with ID %s", teamId.toString())));

        statsService.addPlayer(newPlayerJson, team);

        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @GetMapping("/all-teams")
    public ResponseEntity<?> getPlayersInTeam() {
        var responseBody = new HashMap<>();
        responseBody.put("teamList", statsService.getAllTeams());
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/players-in-team/{teamId}")
    public ResponseEntity<?> getPlayersInTeam(@PathVariable Long teamId) {
        var team = statsService.getTeamFromId(teamId).orElseThrow(
                () -> new NotFoundException(String.format("No team with ID %s", teamId.toString())));

        var responseBody = new HashMap<>();
        responseBody.put("playerList", statsService.getPlayersInTeam(team));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/stats/{teamId}/batting")
    public ResponseEntity<?> fetchBattingStats(@PathVariable Long teamId) {
        var team = statsService.getTeamFromId(teamId).orElseThrow(
                () -> new NotFoundException(String.format("No team with ID %s", teamId.toString())));

        var responseBody = new HashMap<>();
        responseBody.put("stats", statsService.fetchBattingStats(team));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/stats/{teamId}/bowling")
    public ResponseEntity<?> fetchBowlingStats(@PathVariable Long teamId) {
        var team = statsService.getTeamFromId(teamId).orElseThrow(
                () -> new NotFoundException(String.format("No team with ID %s", teamId.toString())));

        var responseBody = new HashMap<>();
        responseBody.put("stats", statsService.fetchBowlingStats(team));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/stats/{teamId}/fielding")
    public ResponseEntity<?> fetchFieldingStats(@PathVariable Long teamId) {
        var team = statsService.getTeamFromId(teamId).orElseThrow(
                () -> new NotFoundException(String.format("No team with ID %s", teamId.toString())));

        var responseBody = new HashMap<>();
        responseBody.put("stats", statsService.fetchFieldingStats(team));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }
}
