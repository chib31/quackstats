package co.uk.cbradbury.quackstats.model.repository;

import co.uk.cbradbury.quackstats.model.entity.Innings;
import co.uk.cbradbury.quackstats.model.entity.Wicket;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WicketRepository extends CrudRepository<Wicket, Long> {
    List<Wicket> findAllByInnings(Innings innings);
}
