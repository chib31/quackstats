package co.uk.cbradbury.quackstats.model.repository;

import co.uk.cbradbury.quackstats.model.entity.Scorecard;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.Optional;

@Repository
public interface ScorecardRepository extends CrudRepository<Scorecard, Long> {
    Optional<Scorecard> findByDateAndFixtureOrder(Date date, int fixtureOrder);
}
