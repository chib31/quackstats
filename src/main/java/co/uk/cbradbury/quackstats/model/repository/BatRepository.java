package co.uk.cbradbury.quackstats.model.repository;

import co.uk.cbradbury.quackstats.model.entity.Bat;
import co.uk.cbradbury.quackstats.model.entity.Innings;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BatRepository extends CrudRepository<Bat, Long> {
    List<Bat> findAllByInnings(Innings innings);
}
