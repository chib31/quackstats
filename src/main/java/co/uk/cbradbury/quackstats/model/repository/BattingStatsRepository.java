package co.uk.cbradbury.quackstats.model.repository;

import co.uk.cbradbury.quackstats.model.view.BattingStats;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface BattingStatsRepository extends CrudRepository<BattingStats, Long> {
    Set<BattingStats> findAllByTeamId(Long teamId);
}
