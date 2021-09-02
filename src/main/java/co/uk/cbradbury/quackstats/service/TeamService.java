package co.uk.cbradbury.quackstats.service;

import co.uk.cbradbury.quackstats.exception.QuackstatsException;
import co.uk.cbradbury.quackstats.json.PlayerJson;
import co.uk.cbradbury.quackstats.json.TeamJson;
import co.uk.cbradbury.quackstats.model.entity.Player;
import co.uk.cbradbury.quackstats.model.entity.Team;
import co.uk.cbradbury.quackstats.model.repository.PlayerRepository;
import co.uk.cbradbury.quackstats.model.repository.TeamRepository;
import co.uk.cbradbury.quackstats.util.EntityJsonMapping;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static co.uk.cbradbury.quackstats.util.EntityJsonMapping.*;

@Service
public class TeamService {

    private final EntityManager entityManager;

    private final TeamRepository teamRepository;

    private final PlayerRepository playerRepository;

    public TeamService(EntityManager entityManager, TeamRepository teamRepository, PlayerRepository playerRepository) {
        this.entityManager = entityManager;
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
    }

    public Optional<Team> fetchTeamById(UUID id) {
        return teamRepository.findById(id);
    }

    //This is a shit hack, but will no longer be required after moving to web-form entry
    public Optional<Team> fetchTeamByApproximateName(String teamName) {
        try {
            var resultFullName = teamRepository.findByApproximateName(teamName);
            if (resultFullName.isPresent()) {
                return resultFullName;
            }

            String simplifiedName = teamName.replace(" CC", "").replace(" XI", "");
            var resultSimplified = teamRepository.findByApproximateName(simplifiedName);
            if (resultSimplified.isPresent()) {
                return resultSimplified;
            }

            String addCC = String.format("%s %s", simplifiedName, "CC");
            var resultAddCC = teamRepository.findByApproximateName(addCC);
            if (resultAddCC.isPresent()) {
                return resultAddCC;
            }

            String addXI = String.format("%s %s", simplifiedName, "XI");
            var resultAddXI = teamRepository.findByApproximateName(addXI);
            if (resultAddXI.isPresent()) {
                return resultAddXI;
            }

            String removedApostrophes = simplifiedName.replace("'", "");
            return teamRepository.findByApproximateName(removedApostrophes);

        } catch (NonUniqueResultException | IncorrectResultSizeDataAccessException e) {
            throw new QuackstatsException(String.format("Multiple existing teams with name similar to %s", teamName));
        }
    }

    public List<TeamJson> fetchAllTeams() {
        var teamJsonList = new ArrayList<TeamJson>();
        for (var team : teamRepository.findAll()) {
            teamJsonList.add(mapTeamToJson(team));
        }
        return teamJsonList;
    }

    public List<Team> fetchTeamsFromList(List<TeamJson> teamJsonList) {
        var existingTeams = new ArrayList<Team>();
        for (TeamJson teamJson : teamJsonList) {
            fetchTeamByApproximateName(teamJson.getName()).ifPresent(existingTeams::add);
        }
        return existingTeams;
    }

    @Transactional
    public void createTeams(List<TeamJson> teamJsonList) {
        teamJsonList.forEach(t -> entityManager.persist(mapTeamFromJson(t)));
    }

    @Transactional
    public void createPlayersInTeam(Team team, List<PlayerJson> playerJsonList) {
        playerJsonList.forEach(p -> entityManager.persist(mapPlayerFromJson(p, team)));
    }

    public List<Player> fetchPlayersByTeam(Team team) {
        return playerRepository.findByMainTeam(team);
    }

    public List<PlayerJson> fetchJsonPlayersByTeam(Team team) {
        return fetchPlayersByTeam(team).stream().map(EntityJsonMapping::mapPlayerToJson).collect(Collectors.toList());
    }

    public Optional<Player> fetchPlayerByNameAndMainTeam(String name, Team team) {
        return playerRepository.findByScorecardNameAndMainTeam(name, team);
    }

    public List<Player> fetchPlayersInTeamFromList(Team team, List<PlayerJson> playerJsonList) {
        List<Player> existingPlayers = new ArrayList<>();
        for (var playerJson : playerJsonList) {
            playerRepository.findByScorecardNameAndMainTeam(playerJson.getScorecardName(), team).ifPresent(
                    existingPlayers::add);
        }
        return existingPlayers;
    }
}
