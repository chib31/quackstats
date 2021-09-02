package co.uk.cbradbury.quackstats.model.repository;

import co.uk.cbradbury.quackstats.model.entity.Scorecard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ScorecardRepository extends JpaRepository<Scorecard, UUID> {
    Optional<Scorecard> findByDateAndFixtureOrder(Date date, int fixtureOrder);
}
