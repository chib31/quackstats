package co.uk.cbradbury.quackstats.model.repository;

import co.uk.cbradbury.quackstats.model.entity.Innings;
import co.uk.cbradbury.quackstats.model.entity.Scorecard;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface InningsRepository extends CrudRepository<Innings, Long> {
    List<Innings> findAllByScorecard(Scorecard scorecard);
}
