package co.uk.cbradbury.quackstats.model.repository;

import co.uk.cbradbury.quackstats.model.entity.Player;
import co.uk.cbradbury.quackstats.model.entity.Team;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends CrudRepository<Player, Long> {
    List<Player> findByMainTeam(Team mainTeam);
}
