package co.uk.cbradbury.quackstats.model.repository;

import co.uk.cbradbury.quackstats.model.view.FieldingStats;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;
import java.util.UUID;

public interface FieldingStatsRepository extends CrudRepository<FieldingStats, UUID> {
    Set<FieldingStats> findAllByTeamId(UUID teamId);
}
