package co.uk.cbradbury.quackstats.util;

import co.uk.cbradbury.quackstats.enums.RecordType;
import co.uk.cbradbury.quackstats.enums.ScorecardStatus;
import co.uk.cbradbury.quackstats.json.*;
import co.uk.cbradbury.quackstats.model.entity.*;

public class EntityJsonMapping {

    public static Team mapTeamFromJson(TeamJson teamJson) {
        var team = teamJson.getId() == null ? new Team() : new Team(teamJson.getId());
        team.setName(teamJson.getName());
        return team;
    }

    public static TeamJson mapTeamToJson(Team team) {
        var teamJson = new TeamJson();
        teamJson.setId(team.getId());
        teamJson.setName(team.getName());
        return teamJson;
    }

    public static Player mapPlayerFromJson(PlayerJson playerJson, Team team) {
        var player = playerJson.getId() == null ? new Player() : new Player(playerJson.getId());
        player.setMainTeam(team);
        player.setFirstName(playerJson.getFirstName());
        player.setMiddleNames(playerJson.getMiddleNames());
        player.setLastName(playerJson.getLastName());
        player.setPreferredName(playerJson.getPreferredName());
        player.setScorecardName(playerJson.getScorecardName());
        player.setMembershipStart(playerJson.getMembershipStart());
        player.setCapNumber(playerJson.getCapNumber());
        player.setShirtNumber(playerJson.getShirtNumber());
        return player;
    }

    public static PlayerJson mapPlayerToJson(Player player) {
        var playerJson = new PlayerJson();
        playerJson.setId(player.getId());
        playerJson.setTeamId(player.getMainTeam().getId());
        playerJson.setFirstName(player.getFirstName());
        playerJson.setMiddleNames(player.getMiddleNames());
        playerJson.setLastName(player.getLastName());
        playerJson.setPreferredName(player.getPreferredName());
        playerJson.setScorecardName(player.getScorecardName());
        playerJson.setMembershipStart(player.getMembershipStart());
        playerJson.setCapNumber(player.getCapNumber());
        playerJson.setShirtNumber(player.getShirtNumber());
        playerJson.setMember(player.getMembershipStart() != null);
        return playerJson;
    }

    public static ScorecardJson mapScorecardToJson(Scorecard scorecard) {
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
        return scorecardJson;
    }

    public static Scorecard mapScorecardFromJson(ScorecardJson scorecardJson, Team team, Team opponent) {
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
        return scorecard;
    }

    public static SquadMemberJson mapSquadMemberToJson(SquadMember squadMember) {
        var squadMemberJson = new SquadMemberJson();
        squadMemberJson.setName(squadMember.getPlayer().getScorecardName());
        squadMemberJson.setCaptain(squadMember.getCaptain());
        squadMemberJson.setKeeper(squadMember.getKeeper());
        return squadMemberJson;
    }

    public static SquadMember mapSquadMemberFromJson(SquadMemberJson squadMemberJson, Scorecard scorecard,
                                                     Player player) {
        var squadMember = new SquadMember();
        squadMember.setScorecard(scorecard);
        squadMember.setPlayer(player);
        squadMember.setCaptain(squadMemberJson.getCaptain());
        squadMember.setKeeper(squadMemberJson.getKeeper());
        return squadMember;
    }

    public static InningsJson mapInningsToJson(Innings innings) {
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
        return inningsJson;
    }

    public static Innings mapInningsFromJson(InningsJson inningsJson, Scorecard scorecard) {
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
        return innings;
    }

    public static BatJson mapBatToJson(Bat bat) {
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

    public static Bat mapBatFromJson(BatJson batJson, Innings innings, SquadMember squadMember) {
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

    public static BowlJson mapBowlToJson(Bowl bowl) {
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

    public static Bowl mapBowlFromJson(BowlJson bowlJson, Innings innings, SquadMember squadMember) {
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

    public static WicketJson mapWicketToJson(Wicket wicket) {
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

    public static Wicket mapWicketFromJson(WicketJson wicketJson, Innings innings, SquadMember squadMemberBowler,
                                           SquadMember squadMemberFielder) {
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
}
