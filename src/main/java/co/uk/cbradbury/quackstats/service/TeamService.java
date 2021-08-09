package co.uk.cbradbury.quackstats.service;

import co.uk.cbradbury.quackstats.json.PlayerJson;
import co.uk.cbradbury.quackstats.json.TeamJson;
import co.uk.cbradbury.quackstats.model.entity.Player;
import co.uk.cbradbury.quackstats.model.entity.Team;
import co.uk.cbradbury.quackstats.model.repository.PlayerRepository;
import co.uk.cbradbury.quackstats.model.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TeamService {

    private final TeamRepository teamRepository;

    private final PlayerRepository playerRepository;

    public TeamService(TeamRepository teamRepository, PlayerRepository playerRepository) {
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
    }

    public Optional<Team> findTeamById(Long teamId) {
        return teamRepository.findById(teamId);
    }

    public Optional<TeamJson> findTeamNameFromUuid(UUID uuid) {
        return teamRepository.findByUuid(uuid).map(this::getTeamJsonFromTeam);
    }

    public Optional<Player> findPlayerById(Long playerId) {
        return playerRepository.findById(playerId);
    }

    public List<PlayerJson> getTeamMembers(Team team) {
        var playerList = playerRepository.findByMainTeam(team);
        return playerList.stream()
                .map(this::playerSelectionFromEntity)
                .collect(Collectors.toList());
    }

    private PlayerJson playerSelectionFromEntity(Player player) {
        var playerSelectionJson = new PlayerJson();
        playerSelectionJson.setId(player.getId());
        playerSelectionJson.setScorecardName(player.getScorecardName());
        playerSelectionJson.setMember(player.getMembershipStart() != null);
        return playerSelectionJson;
    }

    private TeamJson getTeamJsonFromTeam(Team team) {
        var teamJson = new TeamJson();
        teamJson.setId(team.getId());
        teamJson.setName(team.getName());
        return teamJson;
    }
}
