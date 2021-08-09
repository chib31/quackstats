package co.uk.cbradbury.quackstats.model.repository;

import co.uk.cbradbury.quackstats.model.view.BowlingStats;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface BowlingStatsRepository extends CrudRepository<BowlingStats, Long> {
    Set<BowlingStats> findAllByTeamId(Long teamId);
}
