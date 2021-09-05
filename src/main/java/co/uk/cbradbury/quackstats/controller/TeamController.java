package co.uk.cbradbury.quackstats.controller;

import co.uk.cbradbury.quackstats.exception.BadRequestException;
import co.uk.cbradbury.quackstats.exception.NotFoundException;
import co.uk.cbradbury.quackstats.json.PlayerJson;
import co.uk.cbradbury.quackstats.json.TeamJson;
import co.uk.cbradbury.quackstats.model.entity.Player;
import co.uk.cbradbury.quackstats.model.entity.Team;
import co.uk.cbradbury.quackstats.service.TeamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/team")
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping("/{teamId}")
    public ResponseEntity<?> fetchTeamById(@PathVariable String teamId) {
        var team = teamService.fetchTeamById(UUID.fromString(teamId)).orElseThrow(
                () -> new NotFoundException(String.format("No team with ID %s", teamId)));
        return new ResponseEntity<>(team, HttpStatus.OK);
    }

    @GetMapping("/{teamId}/players")
    public ResponseEntity<?> fetchPlayersInTeam(@PathVariable String teamId) {
        var team = teamService.fetchTeamById(UUID.fromString(teamId)).orElseThrow(
                () -> new NotFoundException(String.format("No team with ID %s", teamId)));
        var playerJsonList = teamService.fetchJsonPlayersByTeam(team);
        return new ResponseEntity<>(playerJsonList, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<?> fetchAllTeams() {
        return new ResponseEntity<>(teamService.fetchAllTeams(), HttpStatus.OK);
    }

    @PutMapping("/new")
    public ResponseEntity<?> newTeams(@Valid @RequestBody List<TeamJson> teamJsonList) {
        var duplicateTeams = teamService.fetchTeamsFromList(teamJsonList);
        if (!duplicateTeams.isEmpty()) {
            var duplicateTeamsCsv = duplicateTeams.stream()
                    .map(Team::getName)
                    .collect(Collectors.joining(", "));
            throw new BadRequestException(String.format("The following team(s) already exist: %s", duplicateTeamsCsv));
        }
        teamService.createTeams(teamJsonList);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{teamId}/new-players")
    public ResponseEntity<?> newPlayers(@PathVariable String teamId,
                                        @Valid @RequestBody List<PlayerJson> playerJsonList) {
        var team = teamService.fetchTeamById(UUID.fromString(teamId)).orElseThrow(
                () -> new NotFoundException(String.format("No team with ID %s", teamId)));

        var duplicatePlayers = teamService.fetchPlayersInTeamFromList(team, playerJsonList);
        if (!duplicatePlayers.isEmpty()) {
            var duplicatePlayersCsv = duplicatePlayers.stream()
                    .map(Player::getScorecardName)
                    .collect(Collectors.joining(", "));
            throw new BadRequestException(String.format("The following player(s) already exist in %s: %s",
                    team.getName(), duplicatePlayersCsv));
        }
        teamService.createPlayersInTeam(team, playerJsonList);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
