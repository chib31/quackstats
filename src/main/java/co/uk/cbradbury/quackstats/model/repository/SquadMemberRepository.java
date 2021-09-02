package co.uk.cbradbury.quackstats.model.repository;

import co.uk.cbradbury.quackstats.model.entity.Scorecard;
import co.uk.cbradbury.quackstats.model.entity.SquadMember;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SquadMemberRepository extends CrudRepository<SquadMember, UUID> {
    List<SquadMember> findAllByScorecard(Scorecard scorecard);
}
