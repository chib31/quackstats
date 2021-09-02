package co.uk.cbradbury.quackstats.model.repository;

import co.uk.cbradbury.quackstats.model.entity.Bat;
import co.uk.cbradbury.quackstats.model.entity.Innings;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface BatRepository extends CrudRepository<Bat, UUID> {
    List<Bat> findAllByInnings(Innings innings);
}
