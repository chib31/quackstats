package co.uk.cbradbury.quackstats.service;

import co.uk.cbradbury.quackstats.model.entity.Team;
import co.uk.cbradbury.quackstats.model.repository.BattingStatsRepository;
import co.uk.cbradbury.quackstats.model.repository.BowlingStatsRepository;
import co.uk.cbradbury.quackstats.model.repository.FieldingStatsRepository;
import co.uk.cbradbury.quackstats.model.view.BattingStats;
import co.uk.cbradbury.quackstats.model.view.BowlingStats;
import co.uk.cbradbury.quackstats.model.view.FieldingStats;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class StatsService {

    private final BattingStatsRepository battingStatsRepository;

    private final BowlingStatsRepository bowlingStatsRepository;

    private final FieldingStatsRepository fieldingStatsRepository;

    public StatsService(BattingStatsRepository battingStatsRepository, BowlingStatsRepository bowlingStatsRepository,
                        FieldingStatsRepository fieldingStatsRepository) {
        this.battingStatsRepository = battingStatsRepository;
        this.bowlingStatsRepository = bowlingStatsRepository;
        this.fieldingStatsRepository = fieldingStatsRepository;
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
