package co.uk.cbradbury.quackstats.model.repository;

import co.uk.cbradbury.quackstats.model.view.BowlingStats;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;
import java.util.UUID;

public interface BowlingStatsRepository extends CrudRepository<BowlingStats, UUID> {
    Set<BowlingStats> findAllByTeamId(UUID teamId);
}
