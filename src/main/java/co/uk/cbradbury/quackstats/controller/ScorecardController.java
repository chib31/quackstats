package co.uk.cbradbury.quackstats.controller;

import co.uk.cbradbury.quackstats.service.ScorecardService;
import co.uk.cbradbury.quackstats.service.TeamService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/scorecard")
public class ScorecardController {

    private final ScorecardService scorecardService;

    private final TeamService teamService;

    public ScorecardController(ScorecardService scorecardService, TeamService teamService) {
        this.scorecardService = scorecardService;
        this.teamService = teamService;
    }

//    @PutMapping
//    public ResponseEntity<?> createNewScorecard(@Valid @RequestBody FixtureDetailsJson fixtureDetailsJson) {
//        var team = teamService.findTeamById(fixtureDetailsJson.getTeamId()).orElseThrow(
//                () -> new NotFoundException(String.format("No team with ID %s", fixtureDetailsJson.getTeamId())));
//
//        var opponent = teamService.findTeamById(fixtureDetailsJson.getOpponentId()).orElseThrow(
//                () -> new NotFoundException(String.format("No team with ID %s", fixtureDetailsJson.getOpponentId())));
//
//        var scorecard = scorecardService.createNewScorecard(fixtureDetailsJson, team, opponent);
//
//        return new ResponseEntity<>(scorecard.getId(), HttpStatus.CREATED);
//    }

//    // TODO - implement the pattern used here (i.e. fetching the entities in the controller for validation, then again in the service for usage) in the other endpoints
//    @PostMapping("/{scorecardId}/set-squad-members")
//    public ResponseEntity<?> setSquadMembers(@PathVariable Long scorecardId,
//                                             @RequestBody List<SquadMemberJson> squadMemberJsonList) {
//
//        var scorecard = scorecardService.findScorecardById(scorecardId).orElseThrow(
//                () -> new NotFoundException(String.format("No scorecard with ID %s", scorecardId)));
//
//        for (var squadMemberJson : squadMemberJsonList) {
//            var playerId = squadMemberJson.getPlayerId();
//            teamService.findPlayerById(playerId).orElseThrow(
//                    () -> new NotFoundException(String.format("No player with ID %s", playerId)));
//        }
//
//        var squadMemberList = scorecardService.setSquadMembers(scorecard, squadMemberJsonList);
//
//        return new ResponseEntity<>(squadMemberList, HttpStatus.CREATED);
//    }
//
//    @PutMapping("/{scorecardId}/add-innings")
//    public ResponseEntity<?> addInnings(@PathVariable Long scorecardId,
//                                        @RequestBody InningsJson inningsJson) {
//
//        var scorecard = scorecardService.findScorecardById(scorecardId).orElseThrow(
//                () -> new NotFoundException(String.format("No scorecard with ID %s", scorecardId)));
//
//
//        Set<Long> squadMemberIdSet = new HashSet<>();
//
//        squadMemberIdSet.addAll(inningsJson.getBatSet().stream().map(BatJson::getSquadMemberId)
//                .collect(Collectors.toSet()));
//        squadMemberIdSet.addAll(inningsJson.getBowlSet().stream().map(BowlJson::getSquadMemberId)
//                .collect(Collectors.toSet()));
//
//        Set<WicketJson> wicketList = inningsJson.getWicketSet();
//        squadMemberIdSet.addAll(wicketList.stream().map(WicketJson::getFielderSquadMemberId)
//                .collect(Collectors.toSet()));
//        squadMemberIdSet.addAll(wicketList.stream().map(WicketJson::getBowlerSquadMemberId)
//                .collect(Collectors.toSet()));
//
//        squadMemberIdSet.remove(null);
//
//        for (var squadMemberId : squadMemberIdSet) {
//            scorecardService.findSquadMemberById(squadMemberId).orElseThrow(
//                    () -> new NotFoundException(String.format("No squad member with ID %s", squadMemberId)));
//        }
//
//        var innings = scorecardService.addInnings(scorecard, inningsJson);
//
//        return new ResponseEntity<>(innings.getId(), HttpStatus.CREATED);
//    }

//    @PutMapping("/innings/{inningsId}/add-batting")
//    public ResponseEntity<?> addPlayerBatting(@PathVariable Long inningsId,
//                                              @RequestBody List<BatJson> batJsonList) {
//
//        var innings = scorecardService.findInningsById(inningsId).orElseThrow(
//                () -> new NotFoundException(String.format("No innings found with ID %s", inningsId)));
//
//        for(var newBatJson : batJsonList) {
//            var squadMemberId = newBatJson.getSquadMemberId();
//            scorecardService.findSquadMemberById(squadMemberId).orElseThrow(
//                    () -> new NotFoundException(String.format("No squad member with ID %s", squadMemberId)));
//        }
//
//        scorecardService.addBatList(innings, batJsonList);
//
//        return new ResponseEntity<>(HttpStatus.CREATED);
//    }
//
//    @PutMapping("/innings/{inningsId}/add-bowling")
//    public ResponseEntity<?> addPlayerBowling(@PathVariable Long inningsId,
//                                              @RequestBody List<BowlJson> bowlJsonList) {
//
//        var innings = scorecardService.findInningsById(inningsId).orElseThrow(
//                () -> new NotFoundException(String.format("No innings found with ID %s", inningsId)));
//
//        for(var newBowlJson : bowlJsonList) {
//            var squadMemberId = newBowlJson.getSquadMemberId();
//            scorecardService.findSquadMemberById(squadMemberId).orElseThrow(
//                    () -> new NotFoundException(String.format("No squad member with ID %s", squadMemberId)));
//        }
//
//        scorecardService.addBowlList(innings, bowlJsonList);
//
//        return new ResponseEntity<>(HttpStatus.CREATED);
//    }
//
//    @PutMapping("/innings/{inningsId}/add-wickets")
//    public ResponseEntity<?> addWickets(@PathVariable Long inningsId,
//                                        @RequestBody List<WicketJson> wicketJsonList) {
//
//        var innings = scorecardService.findInningsById(inningsId).orElseThrow(
//                () -> new NotFoundException(String.format("No innings found with ID %s", inningsId)));
//
//        for(var wicketJson : wicketJsonList) {
//            var bowlerSquadMemberId = wicketJson.getBowlerSquadMemberId();
//            scorecardService.findSquadMemberById(bowlerSquadMemberId).orElseThrow(
//                    () -> new NotFoundException(String.format("No squad member with ID %s", bowlerSquadMemberId)));
//
//            var fielderSquadMemberId = wicketJson.getBowlerSquadMemberId();
//            scorecardService.findSquadMemberById(fielderSquadMemberId).orElseThrow(
//                    () -> new NotFoundException(String.format("No squad member with ID %s", fielderSquadMemberId)));
//        }
//
//        scorecardService.addWicketList(innings, wicketJsonList);
//
//        return new ResponseEntity<>(HttpStatus.CREATED);
//    }
}
