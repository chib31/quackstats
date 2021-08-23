package co.uk.cbradbury.quackstats.service;

import co.uk.cbradbury.quackstats.enums.RecordType;
import co.uk.cbradbury.quackstats.enums.ScorecardStatus;
import co.uk.cbradbury.quackstats.exception.QuackstatsException;
import co.uk.cbradbury.quackstats.json.backup.*;
import co.uk.cbradbury.quackstats.model.entity.*;
import co.uk.cbradbury.quackstats.model.repository.*;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    private final TeamRepository teamRepository;

    public BackupService(EntityManager entityManager, ScorecardRepository scorecardRepository,
                         InningsRepository inningsRepository, PlayerRepository playerRepository,
                         SquadMemberRepository squadMemberRepository, BatRepository batRepository,
                         BowlRepository bowlRepository, WicketRepository wicketRepository,
                         TeamRepository teamRepository) {
        this.entityManager = entityManager;
        this.scorecardRepository = scorecardRepository;
        this.inningsRepository = inningsRepository;
        this.playerRepository = playerRepository;
        this.squadMemberRepository = squadMemberRepository;
        this.batRepository = batRepository;
        this.bowlRepository = bowlRepository;
        this.wicketRepository = wicketRepository;
        this.teamRepository = teamRepository;
    }

    public Optional<Scorecard> findScorecardById(Long scorecardId) {
        return scorecardRepository.findById(scorecardId);
    }

    public Optional<Scorecard> fetchScorecardByDateAndOrder(Date date, int fixtureOrder) {
        return scorecardRepository.findByDateAndFixtureOrder(date, fixtureOrder);
    }

    public Optional<Team> findTeamById(Long teamId) {
        return teamRepository.findById(teamId);
    }

    public Optional<Team> findTeamByApproximateName(String teamName) {
        String simplifiedName = teamName
                .replace(" CC", "")
                .replace(" XI", "");
        if (teamRepository.findByApproximateName(simplifiedName).isPresent()) {
            return teamRepository.findByApproximateName(simplifiedName);
        } else {
            String removedApostrophes = simplifiedName.replace("'", "");
            return teamRepository.findByApproximateName(removedApostrophes);
        }
    }

    public Optional<Player> findPlayerByNameAndMainTeam(String name, Team team) {
        return playerRepository.findByScorecardNameAndMainTeam(name, team);
    }

    @Transactional
    public void createFromBackup(ScorecardJson scorecardJson, Team team, Team opponent) {
        var scorecard = new Scorecard();
        scorecard.setRecordType(RecordType.BACKUP);
        scorecard.setStatus(ScorecardStatus.COMPLETE);
        scorecard.setTeam(team);
        scorecard.setOpponent(opponent);
        scorecard.setDate(scorecardJson.getDate());
        scorecard.setFixtureOrder(scorecardJson.getFixtureOrder());
        scorecard.setLocation(scorecardJson.getLocation());
        scorecard.setMatchType(scorecardJson.getMatchType());
        scorecard.setInningsLength(scorecardJson.getInningsLength());
        scorecard.setOverLength(scorecardJson.getOverLength());
        scorecard.setWonToss(scorecardJson.getWonToss());
        scorecard.setBatFirst(scorecardJson.getBatFirst());
        scorecard.setResultType(scorecardJson.getResultType());
        
        var squadMemberList = scorecardJson.getSquadMemberList().stream()
                .map(e -> constructSquadMemberFromJson(e, scorecard, team)).collect(Collectors.toList());
        
        var inningsList = scorecardJson.getInningsList().stream()
                .map(e -> constructInningsFromJson(e, scorecard, squadMemberList)).collect(Collectors.toList());

        scorecard.setSquadMemberList(squadMemberList);
        scorecard.setInningsList(inningsList);

        entityManager.persist(scorecard);
    }

    public List<ScorecardJson> fetchAllScorecardsJson() {
        var scorecards = scorecardRepository.findAll();

        List<ScorecardJson> scorecardJsons = new ArrayList<>();
        for (var scorecard : scorecards) {
            scorecardJsons.add(fetchScorecardJson(scorecard));
        }

        return scorecardJsons;
    }

    public ScorecardJson fetchScorecardJson(Scorecard scorecard) {
        var scorecardJson = new ScorecardJson();

        scorecardJson.setTeamId(scorecard.getTeam().getId());
        scorecardJson.setScorecardStatus(scorecard.getStatus());
        scorecardJson.setOpponentName(scorecard.getOpponent().getName());
        scorecardJson.setDate(scorecard.getDate());
        scorecardJson.setFixtureOrder(scorecard.getFixtureOrder());
        scorecardJson.setLocation(scorecard.getLocation());
        scorecardJson.setMatchType(scorecard.getMatchType());
        scorecardJson.setInningsLength(scorecard.getInningsLength());
        scorecardJson.setOverLength(scorecard.getOverLength());
        scorecardJson.setWonToss(scorecard.getWonToss());
        scorecardJson.setBatFirst(scorecard.getBatFirst());
        scorecardJson.setResultType(scorecard.getResultType());

        scorecardJson.setSquadMemberList(constructSquadMemberJsonListFromScorecard(scorecard));
        scorecardJson.setInningsList(constructInningsJsonListFromScorecard(scorecard));

        return scorecardJson;
    }

    private List<SquadMemberJson> constructSquadMemberJsonListFromScorecard(Scorecard scorecard) {
        var squadMembersList = squadMemberRepository.findAllByScorecard(scorecard);
        return squadMembersList.stream().map(this::convertSquadMemberToJson).collect(Collectors.toList());
    }

    private SquadMemberJson convertSquadMemberToJson(SquadMember squadMember) {
        return new SquadMemberJson(
                squadMember.getPlayer().getScorecardName(),
                squadMember.getCaptain(),
                squadMember.getKeeper());
    }
    
    private SquadMember  constructSquadMemberFromJson(SquadMemberJson squadMemberJson, Scorecard scorecard, Team team) {
        var player = playerRepository.findByScorecardNameAndMainTeam(squadMemberJson.getName(), team)
                .orElseThrow(() -> new QuackstatsException(String.format("No player with name %s found in team %s",
                        squadMemberJson.getName(), team.getName())));
        
        var squadMember = new SquadMember();
        squadMember.setScorecard(scorecard);
        squadMember.setPlayer(player);
        squadMember.setCaptain(squadMemberJson.getCaptain());
        squadMember.setKeeper(squadMemberJson.getKeeper());
        return squadMember;
    }

    private List<InningsJson> constructInningsJsonListFromScorecard(Scorecard scorecard) {
        var inningsList = inningsRepository.findAllByScorecard(scorecard);
        return inningsList.stream().map(this::convertInningsToJson).collect(Collectors.toList());
    }

    private InningsJson convertInningsToJson(Innings innings) {
        var inningsJson = new InningsJson();
        inningsJson.setInningsOrder(innings.getInningsOrder());
        inningsJson.setTeamIsBatting(innings.getTeamIsBatting());
        inningsJson.setDeliveries(innings.getDeliveries());
        inningsJson.setWickets(innings.getWickets());
        inningsJson.setRuns(innings.getRuns());
        inningsJson.setByes(innings.getByes());
        inningsJson.setLegByes(innings.getLegByes());
        inningsJson.setWides(innings.getWides());
        inningsJson.setNoBalls(innings.getNoBalls());
        inningsJson.setPenaltyRuns(innings.getPenaltyRuns());

        inningsJson.setBatList(constructBatJsonListFromInnings(innings));
        inningsJson.setBowlList(constructBowlJsonListFromInnings(innings));
        inningsJson.setWicketList(constructWicketJsonListFromInnings(innings));

        return inningsJson;
    }
    
    private Innings constructInningsFromJson(InningsJson inningsJson, Scorecard scorecard,
                                             List<SquadMember> squadMemberList) {
        var innings = new Innings();
        innings.setScorecard(scorecard);
        innings.setInningsOrder(inningsJson.getInningsOrder());
        innings.setTeamIsBatting(inningsJson.getTeamIsBatting());
        innings.setDeliveries(inningsJson.getDeliveries());
        innings.setWickets(inningsJson.getWickets());
        innings.setRuns(inningsJson.getRuns());
        innings.setByes(inningsJson.getByes());
        innings.setLegByes(inningsJson.getLegByes());
        innings.setWides(inningsJson.getWides());
        innings.setNoBalls(inningsJson.getNoBalls());
        innings.setPenaltyRuns(inningsJson.getPenaltyRuns());

        if (inningsJson.getBatList() != null) {
            var batList = inningsJson.getBatList().stream()
                    .map(e -> constructBatFromJson(e, innings, squadMemberList))
                    .collect(Collectors.toList());

            innings.setBatList(batList);
        }

        if (inningsJson.getBowlList() != null) {
            var bowlList = inningsJson.getBowlList().stream()
                    .map(e -> constructBowlFromJson(e, innings, squadMemberList))
                    .collect(Collectors.toList());

            innings.setBowlList(bowlList);
        }

        if (inningsJson.getWicketList() != null) {
            var wicketList = inningsJson.getWicketList().stream()
                    .map(e -> constructWicketFromJson(e, innings, squadMemberList))
                    .collect(Collectors.toList());

            innings.setWicketList(wicketList);
        }

        return innings;
    }
    
    private Bat constructBatFromJson(BatJson batJson, Innings innings, List<SquadMember> squadMemberList) {
        var squadMember = findSquadMemberInListByName(squadMemberList, batJson.getName());
        
        var bat = new Bat();
        bat.setSquadMember(squadMember);
        bat.setInnings(innings);
        bat.setPosition(batJson.getPosition());
        bat.setDeliveries(batJson.getDeliveries());
        bat.setRuns(batJson.getRuns());
        bat.setFours(batJson.getFours());
        bat.setSixes(batJson.getSixes());
        bat.setBattingConclusion(batJson.getBattingConclusion());
        bat.setWicketFielder(batJson.getWicketFielder());
        bat.setWicketBowler(batJson.getWicketBowler());
        bat.setWhereCaught(batJson.getWhereCaught());
        return bat;
    }
    
    private Bowl constructBowlFromJson(BowlJson bowlJson, Innings innings, List<SquadMember> squadMemberList) {
        var squadMember = findSquadMemberInListByName(squadMemberList, bowlJson.getName());
        
        var bowl = new Bowl();
        bowl.setSquadMember(squadMember);
        bowl.setInnings(innings);
        bowl.setBowlerNumber(bowlJson.getBowlerNumber());
        bowl.setDeliveries(bowlJson.getDeliveries());
        bowl.setMaidens(bowlJson.getMaidens());
        bowl.setRuns(bowlJson.getRuns());
        bowl.setWickets(bowlJson.getWickets());
        bowl.setWides(bowlJson.getWides());
        bowl.setNoBalls(bowlJson.getNoBalls());
        bowl.setHatTricks(bowlJson.getHatTricks());
        return bowl;
    }

    private Wicket constructWicketFromJson(WicketJson wicketJson, Innings innings, List<SquadMember> squadMemberList) {
        var squadMemberBowler = findSquadMemberInListByName(squadMemberList, wicketJson.getBowlerName());
        var squadMemberFielder = findSquadMemberInListByName(squadMemberList, wicketJson.getFielderName());

        var wicket = new Wicket();
        wicket.setInnings(innings);
        wicket.setBowler(squadMemberBowler);
        wicket.setFielder(squadMemberFielder);
        wicket.setBattingConclusion(wicketJson.getBattingConclusion());
        wicket.setBattingPosition(wicketJson.getBattingPosition());
        wicket.setBatterRuns(wicketJson.getBatterRuns());
        wicket.setFieldingLocation(wicketJson.getFieldingLocation());
        return wicket;
    }
    
    private SquadMember findSquadMemberInListByName(List<SquadMember> squadMemberList, String name) {
        return squadMemberList.stream()
                .filter(e -> e.getPlayer().getScorecardName().equals(name))
                .findAny()
                .orElse(null);
    }

    private List<BatJson> constructBatJsonListFromInnings(Innings innings) {
        var batList = batRepository.findAllByInnings(innings);
        return batList.stream().map(this::convertBatToJson).collect(Collectors.toList());
    }

    private BatJson convertBatToJson(Bat bat) {
        var batJson = new BatJson();
        batJson.setName(bat.getSquadMember().getPlayer().getScorecardName());
        batJson.setPosition(bat.getPosition());
        batJson.setDeliveries(bat.getDeliveries());
        batJson.setRuns(bat.getRuns());
        batJson.setFours(bat.getFours());
        batJson.setSixes(bat.getSixes());
        batJson.setBattingConclusion(bat.getBattingConclusion());
        batJson.setWicketFielder(bat.getWicketFielder());
        batJson.setWicketBowler(bat.getWicketBowler());
        batJson.setWhereCaught(bat.getWhereCaught());
        return batJson;
    }

    private List<BowlJson> constructBowlJsonListFromInnings(Innings innings) {
        var bowlList = bowlRepository.findAllByInnings(innings);
        return bowlList.stream().map(this::convertBowlToJson).collect(Collectors.toList());
    }

    private BowlJson convertBowlToJson(Bowl bowl) {
        var bowlJson = new BowlJson();
        bowlJson.setName(bowl.getSquadMember().getPlayer().getScorecardName());
        bowlJson.setBowlerNumber(bowl.getBowlerNumber());
        bowlJson.setDeliveries(bowl.getDeliveries());
        bowlJson.setMaidens(bowl.getMaidens());
        bowlJson.setRuns(bowl.getRuns());
        bowlJson.setWickets(bowl.getWickets());
        bowlJson.setWides(bowl.getWides());
        bowlJson.setNoBalls(bowl.getNoBalls());
        bowlJson.setHatTricks(bowl.getHatTricks());
        return bowlJson;
    }

    private List<WicketJson> constructWicketJsonListFromInnings(Innings innings) {
        var wicketList = wicketRepository.findAllByInnings(innings);
        return wicketList.stream().map(this::convertWicketToJson).collect(Collectors.toList());
    }

    private WicketJson convertWicketToJson(Wicket wicket) {
        var wicketJson = new WicketJson();

        var bowlerName = wicket.getBowler() == null
                ? null
                : wicket.getBowler().getPlayer().getScorecardName();
        var fielderName = wicket.getFielder() == null
                ? null
                : wicket.getFielder().getPlayer().getScorecardName();

        wicketJson.setBowlerName(bowlerName);
        wicketJson.setFielderName(fielderName);
        wicketJson.setBattingConclusion(wicket.getBattingConclusion());
        wicketJson.setBattingPosition(wicket.getBattingPosition());
        wicketJson.setBatterRuns(wicket.getBatterRuns());
        wicketJson.setFieldingLocation(wicket.getFieldingLocation());

        return wicketJson;
    }
}
