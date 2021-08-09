package co.uk.cbradbury.quackstats.service;

import co.uk.cbradbury.quackstats.model.entity.Scorecard;
import co.uk.cbradbury.quackstats.model.repository.InningsRepository;
import co.uk.cbradbury.quackstats.model.repository.SquadMemberRepository;
import org.springframework.stereotype.Service;

@Service
public class BackupService {

    private final InningsRepository inningsRepository;

    private final SquadMemberRepository squadMemberRepository;

    public BackupService(InningsRepository inningsRepository, SquadMemberRepository squadMemberRepository) {
        this.inningsRepository = inningsRepository;
        this.squadMemberRepository = squadMemberRepository;
    }

    public Scorecard getFullScorecard(Scorecard scorecard) {

        return scorecard;

//        var fullScorecardJson = new FullScorecardJson();
//
//        fullScorecardJson.setScorecardId(scorecard.getId());
//        fullScorecardJson.setRecordType(scorecard.getRecordType());
//        fullScorecardJson.setScorecardStatus(scorecard.getStatus());
//        fullScorecardJson.setTeamId(scorecard.getTeam().getId());
//        fullScorecardJson.setOpponentId(scorecard.getOpponent().getId());
//
//        fullScorecardJson.setDate(scorecard.getDate());
//        fullScorecardJson.setFixtureOrder(scorecard.getFixtureOrder());
//        fullScorecardJson.setLocation(scorecard.getLocation());
//        fullScorecardJson.setMatchType(scorecard.getMatchType());
//        fullScorecardJson.setInningsLength(scorecard.getInningsLength());
//        fullScorecardJson.setOverLength(scorecard.getOverLength());
//        fullScorecardJson.setWonToss(scorecard.getWonToss());
//        fullScorecardJson.setBatFirst(scorecard.getBatFirst());
//        fullScorecardJson.setResultType(scorecard.getResultType());
//
//        fullScorecardJson.setInningsSet(inningsRepository.findAllByScorecard(scorecard));
//        fullScorecardJson.setSquadMemberSet(squadMemberRepository.findAllByScorecard(scorecard));
//
//        return fullScorecardJson;
    }
}
