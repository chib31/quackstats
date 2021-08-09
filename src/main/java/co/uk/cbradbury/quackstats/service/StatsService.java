package co.uk.cbradbury.quackstats.service;

import co.uk.cbradbury.quackstats.json.PlayerJson;
import co.uk.cbradbury.quackstats.model.entity.Player;
import co.uk.cbradbury.quackstats.model.entity.Team;
import co.uk.cbradbury.quackstats.model.repository.*;
import co.uk.cbradbury.quackstats.model.view.BattingStats;
import co.uk.cbradbury.quackstats.model.view.BowlingStats;
import co.uk.cbradbury.quackstats.model.view.FieldingStats;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class StatsService {

    private final EntityManager entityManager;

    private final TeamRepository teamRepository;

    private final PlayerRepository playerRepository;

    private final BattingStatsRepository battingStatsRepository;

    private final BowlingStatsRepository bowlingStatsRepository;

    private final FieldingStatsRepository fieldingStatsRepository;

    public StatsService(EntityManager entityManager,
                        TeamRepository teamRepository,
                        PlayerRepository playerRepository,
                        BattingStatsRepository battingStatsRepository,
                        BowlingStatsRepository bowlingStatsRepository,
                        FieldingStatsRepository fieldingStatsRepository) {
        this.entityManager = entityManager;
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
        this.battingStatsRepository = battingStatsRepository;
        this.bowlingStatsRepository = bowlingStatsRepository;
        this.fieldingStatsRepository = fieldingStatsRepository;
    }

    public Optional<Team> getTeamFromId(Long teamId) {
        return teamRepository.findById(teamId);
    }

    public boolean teamNameExists(String name) {
        return teamRepository.existsByName(name);
    }

    public List<Team> getAllTeams() {
        var teamList = new ArrayList<Team>();
        teamRepository.findAll().forEach(teamList::add);
        return teamList;
    }

    @Transactional
    public void addTeam(String name) {
        var newTeam = new Team();
        newTeam.setName(name);

        entityManager.persist(newTeam);
    }

    @Transactional
    public void addPlayer(PlayerJson playerJson, Team team) {
        var newPlayer = new Player();
        newPlayer.setFirstName(playerJson.getFirstName());
        newPlayer.setMiddleNames(playerJson.getMiddleNames());
        newPlayer.setLastName(playerJson.getLastName());
        newPlayer.setPreferredName(playerJson.getPreferredName());
        newPlayer.setMembershipStart(playerJson.getMembershipStart());
        newPlayer.setCapNumber(playerJson.getCapNumber());
        newPlayer.setShirtNumber(playerJson.getShirtNumber());
        newPlayer.setMainTeam(team);

        entityManager.persist(newPlayer);
    }

    public List<Player> getPlayersInTeam(Team team) {
        return playerRepository.findByMainTeam(team);
    }

    public Set<BattingStats> fetchBattingStats(Team team) {
        return battingStatsRepository.findAllByTeamId(team.getId());
    }

    public Set<BowlingStats> fetchBowlingStats(Team team) {
        return bowlingStatsRepository.findAllByTeamId(team.getId());
    }

    public Set<FieldingStats> fetchFieldingStats(Team team) {
        return fieldingStatsRepository.findAllByTeamId(team.getId());
    }
}
