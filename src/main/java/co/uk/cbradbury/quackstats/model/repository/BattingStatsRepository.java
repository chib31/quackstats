package co.uk.cbradbury.quackstats.model.repository;

import co.uk.cbradbury.quackstats.model.view.BattingStats;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;
import java.util.UUID;

public interface BattingStatsRepository extends CrudRepository<BattingStats, UUID> {
    Set<BattingStats> findAllByTeamId(UUID teamId);
}
