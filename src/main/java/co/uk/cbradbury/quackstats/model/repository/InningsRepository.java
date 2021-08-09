package co.uk.cbradbury.quackstats.model.repository;

import co.uk.cbradbury.quackstats.model.entity.Innings;
import org.springframework.data.repository.CrudRepository;

public interface InningsRepository extends CrudRepository<Innings, Long> {
}
