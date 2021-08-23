package co.uk.cbradbury.quackstats.service;

import co.uk.cbradbury.quackstats.enums.RecordType;
import co.uk.cbradbury.quackstats.enums.ScorecardStatus;
import co.uk.cbradbury.quackstats.json.backup.ScorecardJson;
import co.uk.cbradbury.quackstats.model.entity.Innings;
import co.uk.cbradbury.quackstats.model.entity.Scorecard;
import co.uk.cbradbury.quackstats.model.entity.SquadMember;
import co.uk.cbradbury.quackstats.model.entity.Team;
import co.uk.cbradbury.quackstats.model.repository.InningsRepository;
import co.uk.cbradbury.quackstats.model.repository.ScorecardRepository;
import co.uk.cbradbury.quackstats.model.repository.SquadMemberRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.Optional;

@Service
public class ScorecardService {

    private final ScorecardRepository scorecardRepository;

    private final SquadMemberRepository squadMemberRepository;

    private final InningsRepository inningsRepository;

    private final EntityManager entityManager;

    public ScorecardService(ScorecardRepository scorecardRepository,
                            SquadMemberRepository squadMemberRepository,
                            InningsRepository inningsRepository, EntityManager entityManager) {
        this.scorecardRepository = scorecardRepository;
        this.squadMemberRepository = squadMemberRepository;
        this.inningsRepository = inningsRepository;
        this.entityManager = entityManager;
    }

    public Optional<SquadMember> findSquadMemberById(Long squadMemberId) {
        return squadMemberRepository.findById(squadMemberId);
    }

    public Optional<Innings> findInningsById(Long inningsId) {
        return inningsRepository.findById(inningsId);
    }

    public void createNewScorecardFull(ScorecardJson scorecardJson, Team team, Team opponent) {

        Scorecard scorecard = new Scorecard();

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

        entityManager.persist(scorecard);
    }

//    @Transactional
//    public Scorecard createNewScorecard(FixtureDetailsJson fixtureDetailsJson, Team team, Team opponent) {
//
//        Scorecard scorecard = new Scorecard();
//
//        scorecard.setRecordType(RecordType.WEB_FORM);
//        scorecard.setStatus(ScorecardStatus.IN_PROGRESS);
//        scorecard.setTeam(team);
//        scorecard.setOpponent(opponent);
//        scorecard.setDate(fixtureDetailsJson.getDate());
//        scorecard.setFixtureOrder(fixtureDetailsJson.getFixtureOrder());
//        scorecard.setLocation(fixtureDetailsJson.getLocation());
//        scorecard.setMatchType(fixtureDetailsJson.getMatchType());
//        scorecard.setInningsLength(fixtureDetailsJson.getInningsLength());
//        scorecard.setOverLength(fixtureDetailsJson.getOverLength());
//        scorecard.setWonToss(fixtureDetailsJson.getWonToss());
//        scorecard.setBatFirst(fixtureDetailsJson.getBatFirst());
//        scorecard.setResultType(fixtureDetailsJson.getResultType());
//
//        entityManager.persist(scorecard);
//
//        return scorecard;
//    }
//
//    @Transactional
//    public List<SquadMemberJson> setSquadMembers(Scorecard scorecard,
//                                                 List<SquadMemberJson> squadMemberJsonList) {
//        List<SquadMemberJson> squadMemberResponseJsonList = new ArrayList<>();
//        for (var newSquadMemberJson : squadMemberJsonList) {
//            var player = playerRepository.findById(newSquadMemberJson.getPlayerId())
//                    .orElseThrow(QuackstatsException::new);
//
//            var squadMember = new SquadMember();
//            squadMember.setScorecard(scorecard);
//            squadMember.setPlayer(player);
//            squadMember.setCaptain(newSquadMemberJson.getCaptain());
//            squadMember.setKeeper(newSquadMemberJson.getKeeper());
//            entityManager.persist(squadMember);
//
//            var squadMemberResponseJson = new SquadMemberJson();
//            squadMemberResponseJson.setId(squadMember.getId());
//            squadMemberResponseJson.setName(player.getScorecardName());
//            squadMemberResponseJson.setCaptain(newSquadMemberJson.getCaptain());
//            squadMemberResponseJson.setKeeper(newSquadMemberJson.getKeeper());
//
//            squadMemberResponseJsonList.add(squadMemberResponseJson);
//        }
//        return squadMemberResponseJsonList;
//    }
//
//    @Transactional
//    public Innings addInnings(Scorecard scorecard, InningsJson inningsJson) {
//        var innings = new Innings();
//        innings.setScorecard(scorecard);
//        innings.setInningsOrder(inningsJson.getInningsOrder());
//        innings.setTeamIsBatting(inningsJson.getTeamIsBatting());
//        innings.setDeliveries(inningsJson.getDeliveries());
//        innings.setWickets(inningsJson.getWickets());
//        innings.setRuns(inningsJson.getRuns());
//        innings.setByes(inningsJson.getByes());
//        innings.setLegByes(inningsJson.getLegByes());
//        innings.setWides(inningsJson.getWides());
//        innings.setNoBalls(inningsJson.getNoBalls());
//
//        entityManager.persist(innings);
//
//        addBatList(innings, inningsJson.getBatSet());
//        addBowlList(innings, inningsJson.getBowlSet());
//        addWicketList(innings, inningsJson.getWicketSet());
//
//        return innings;
//    }
//
//    private void addBatList(Innings innings, Set<BatJson> batJsonList) {
//
//        for(var newBatJson : batJsonList) {
//
//            var squadMember = squadMemberRepository.findById(newBatJson.getSquadMemberId())
//                    .orElseThrow(QuackstatsException::new);
//
//            var bat = new Bat();
//            bat.setSquadMember(squadMember);
//            bat.setInnings(innings);
//            bat.setPosition(newBatJson.getPosition());
//            bat.setDeliveries(newBatJson.getDeliveries());
//            bat.setRuns(newBatJson.getRuns());
//            bat.setFours(newBatJson.getFours());
//            bat.setSixes(newBatJson.getSixes());
//            bat.setBattingConclusion(newBatJson.getBattingConclusion());
//            bat.setWicketFielder(newBatJson.getWicketFielder());
//            bat.setWicketBowler(newBatJson.getWicketBowler());
//            bat.setWhereCaught(newBatJson.getWhereCaught());
//
//            entityManager.persist(bat);
//        }
//    }
//
//    private void addBowlList(Innings innings, Set<BowlJson> bowlJsonList) {
//
//        for (var newBowlJson : bowlJsonList) {
//
//            var squadMember = squadMemberRepository.findById(newBowlJson.getSquadMemberId())
//                    .orElseThrow(QuackstatsException::new);
//
//            var bowl = new Bowl();
//            bowl.setSquadMember(squadMember);
//            bowl.setInnings(innings);
//            bowl.setBowlerNumber(newBowlJson.getBowlerNumber());
//            bowl.setDeliveries(newBowlJson.getDeliveries());
//            bowl.setMaidens(newBowlJson.getMaidens());
//            bowl.setRuns(newBowlJson.getRuns());
//            bowl.setWides(newBowlJson.getWides());
//            bowl.setNoBalls(newBowlJson.getNoBalls());
//            bowl.setHatTricks(newBowlJson.getHatTricks());
//
//            entityManager.persist(bowl);
//        }
//    }
//
//    private void addWicketList(Innings innings, Set<WicketJson> wicketJsonList) {
//
//        for (var newWicketJson : wicketJsonList) {
//            var wicket = new Wicket();
//
//            wicket.setInnings(innings);
//            if (newWicketJson.getBowlerSquadMemberId() != null) {
//                wicket.setBowler(squadMemberRepository.findById(newWicketJson.getBowlerSquadMemberId())
//                        .orElseThrow(QuackstatsException::new));
//            }
//            if (newWicketJson.getFielderSquadMemberId() != null) {
//                wicket.setFielder(squadMemberRepository.findById(newWicketJson.getFielderSquadMemberId())
//                        .orElseThrow(QuackstatsException::new));
//            }
//            wicket.setBattingConclusion(newWicketJson.getBattingConclusion());
//            wicket.setBattingPosition(newWicketJson.getBattingPosition());
//            wicket.setBatterRuns(newWicketJson.getBatterRuns());
//            wicket.setFieldingLocation(newWicketJson.getFieldingLocation());
//
//            entityManager.persist(wicket);
//        }
//    }
}
