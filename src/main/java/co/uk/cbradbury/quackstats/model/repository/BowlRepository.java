package co.uk.cbradbury.quackstats.model.repository;

import co.uk.cbradbury.quackstats.model.entity.Bowl;
import co.uk.cbradbury.quackstats.model.entity.Innings;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BowlRepository extends CrudRepository<Bowl, Long> {
    List<Bowl> findAllByInnings(Innings innings);
}
