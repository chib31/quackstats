package co.uk.cbradbury.quackstats.service;

import co.uk.cbradbury.quackstats.exception.QuackstatsException;
import co.uk.cbradbury.quackstats.json.*;
import co.uk.cbradbury.quackstats.model.entity.*;
import co.uk.cbradbury.quackstats.model.repository.*;
import co.uk.cbradbury.quackstats.util.EntityJsonMapping;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static co.uk.cbradbury.quackstats.util.EntityJsonMapping.*;

@Service
public class BackupService {

    private final EntityManager entityManager;

    private final ScorecardRepository scorecardRepository;

    private final InningsRepository inningsRepository;

    private final PlayerRepository playerRepository;

    private final SquadMemberRepository squadMemberRepository;

    private final BatRepository batRepository;

    private final BowlRepository bowlRepository;

    private final WicketRepository wicketRepository;

    public BackupService(EntityManager entityManager, ScorecardRepository scorecardRepository,
                         InningsRepository inningsRepository, PlayerRepository playerRepository,
                         SquadMemberRepository squadMemberRepository, BatRepository batRepository,
                         BowlRepository bowlRepository, WicketRepository wicketRepository) {
        this.entityManager = entityManager;
        this.scorecardRepository = scorecardRepository;
        this.inningsRepository = inningsRepository;
        this.playerRepository = playerRepository;
        this.squadMemberRepository = squadMemberRepository;
        this.batRepository = batRepository;
        this.bowlRepository = bowlRepository;
        this.wicketRepository = wicketRepository;
    }

    public Optional<Scorecard> findScorecardById(UUID scorecardId) {
        return scorecardRepository.findById(scorecardId);
    }

    public Optional<Scorecard> fetchScorecardByDateAndOrder(Date date, int fixtureOrder) {
        return scorecardRepository.findByDateAndFixtureOrder(date, fixtureOrder);
    }

    @Transactional
    public void importScorecard(ScorecardJson scorecardJson, Team team, Team opponent) {
        var scorecard = mapScorecardFromJson(scorecardJson, team, opponent);
        entityManager.persist(scorecard);

        var squadMemberList = scorecardJson.getSquadMemberList().stream()
                .map(sm -> createSquadMemberFromJson(sm, scorecard))
                .collect(Collectors.toList());
        squadMemberList.forEach(entityManager::persist);

        scorecardJson.getInningsList().forEach(i -> constructInningsFromJson(i, scorecard, squadMemberList));
    }

    public ScorecardJson constructScorecardJson(Scorecard scorecard) {
        var scorecardJson = mapScorecardToJson(scorecard);

        var squadMemberJsonList = squadMemberRepository.findAllByScorecard(scorecard).stream()
                .map(EntityJsonMapping::mapSquadMemberToJson)
                .collect(Collectors.toList());
        scorecardJson.setSquadMemberList(squadMemberJsonList);

        var inningsJsonList = inningsRepository.findAllByScorecard(scorecard).stream()
                .map(this::constructInningsJson)
                .collect(Collectors.toList());
        scorecardJson.setInningsList(inningsJsonList);

        return scorecardJson;
    }

    public List<ScorecardJson> constructAllScorecardsJson() {
        return scorecardRepository.findAll().stream()
                .map(this::constructScorecardJson)
                .collect(Collectors.toList());
    }

    private InningsJson constructInningsJson(Innings innings) {
        var inningsJson = mapInningsToJson(innings);

        if (innings.getTeamIsBatting()) {
            var batJsonList = batRepository.findAllByInnings(innings).stream()
                    .map(EntityJsonMapping::mapBatToJson)
                    .collect(Collectors.toList());
            inningsJson.setBatList(batJsonList);

        } else {
            var bowlJsonList = bowlRepository.findAllByInnings(innings).stream()
                    .map(EntityJsonMapping::mapBowlToJson)
                    .collect(Collectors.toList());
            inningsJson.setBowlList(bowlJsonList);

            var wicketJsonList = wicketRepository.findAllByInnings(innings).stream()
                    .map(EntityJsonMapping::mapWicketToJson)
                    .collect(Collectors.toList());
            inningsJson.setWicketList(wicketJsonList);
        }

        return inningsJson;
    }

    private SquadMember createSquadMemberFromJson(SquadMemberJson squadMemberJson, Scorecard scorecard) {
        var player = playerRepository.findByScorecardNameAndMainTeam(squadMemberJson.getName(),
                scorecard.getTeam()).orElseThrow(
                () -> new QuackstatsException(String.format("No player with name %s found in team %s",
                        squadMemberJson.getName(), scorecard.getTeam().getName())));

        return mapSquadMemberFromJson(squadMemberJson, scorecard, player);
    }

    private void constructInningsFromJson(InningsJson inningsJson, Scorecard scorecard,
                                             List<SquadMember> squadMemberList) {
        var innings = mapInningsFromJson(inningsJson, scorecard);
        entityManager.persist(innings);

        if (innings.getTeamIsBatting()) {
            inningsJson.getBatList().stream()
                    .map(e -> constructBatFromJson(e, innings, squadMemberList))
                    .forEach(entityManager::persist);
        } else {
            inningsJson.getBowlList().stream()
                    .map(e -> constructBowlFromJson(e, innings, squadMemberList))
                    .forEach(entityManager::persist);

            inningsJson.getWicketList().stream()
                    .map(e -> constructWicketFromJson(e, innings, squadMemberList))
                    .forEach(entityManager::persist);
        }
    }

    private Bat constructBatFromJson(BatJson batJson, Innings innings, List<SquadMember> squadMemberList) {
        var squadMember = findSquadMemberInListByName(squadMemberList, batJson.getName());
        return mapBatFromJson(batJson, innings, squadMember);
    }

    private Bowl constructBowlFromJson(BowlJson bowlJson, Innings innings, List<SquadMember> squadMemberList) {
        var squadMember = findSquadMemberInListByName(squadMemberList, bowlJson.getName());
        return mapBowlFromJson(bowlJson, innings, squadMember);
    }

    private Wicket constructWicketFromJson(WicketJson wicketJson, Innings innings, List<SquadMember> squadMemberList) {
        var squadMemberBowler = findSquadMemberInListByName(squadMemberList, wicketJson.getBowlerName());
        var squadMemberFielder = findSquadMemberInListByName(squadMemberList, wicketJson.getFielderName());
        return mapWicketFromJson(wicketJson, innings, squadMemberBowler, squadMemberFielder);
    }

    private SquadMember findSquadMemberInListByName(List<SquadMember> squadMemberList, String name) {
        return squadMemberList.stream()
                .filter(e -> e.getPlayer().getScorecardName().equals(name))
                .findAny()
                .orElse(null);
    }
}
