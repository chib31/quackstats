package co.uk.cbradbury.quackstats.controller;

import co.uk.cbradbury.quackstats.exception.NotFoundException;
import co.uk.cbradbury.quackstats.service.TeamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/team")
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping("/{teamId}/team-members")
    public ResponseEntity<?> getTeamMembers(@PathVariable Long teamId) {
        var team = teamService.findTeamById(teamId).orElseThrow(
                () -> new NotFoundException(String.format("No team with ID %s", teamId)));
        var response = teamService.getTeamMembers(team);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<?> getTeam(@PathVariable UUID uuid) {
        var teamJson = teamService.findTeamNameFromUuid(uuid).orElseThrow(
                () -> new NotFoundException(String.format("No team with UUID %s", uuid)));
        return new ResponseEntity<>(teamJson, HttpStatus.OK);
    }
}
