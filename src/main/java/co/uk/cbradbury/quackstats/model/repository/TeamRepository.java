package co.uk.cbradbury.quackstats.model.repository;

import co.uk.cbradbury.quackstats.model.entity.Team;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TeamRepository extends CrudRepository<Team, Long> {
    boolean existsByName(String name);

    Optional<Team> findByUuid(UUID uuid);
}
