package co.uk.cbradbury.quackstats.model.repository;

import co.uk.cbradbury.quackstats.model.view.FieldingStats;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface FieldingStatsRepository extends CrudRepository<FieldingStats, Long> {
    Set<FieldingStats> findAllByTeamId(Long teamId);
}
