package co.uk.cbradbury.quackstats.model.entity;

import javax.persistence.*;
import java.util.UUID;

@Entity(name = "squad_member")
public class SquadMember {

    @Id
    private final UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id")
    private Player player;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scorecard_id")
    private Scorecard scorecard;

    private Boolean captain;

    private Boolean keeper;

    public SquadMember() {
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Scorecard getScorecard() {
        return scorecard;
    }

    public void setScorecard(Scorecard scorecard) {
        this.scorecard = scorecard;
    }

    public Boolean getCaptain() {
        return captain;
    }

    public void setCaptain(Boolean captain) {
        this.captain = captain;
    }

    public Boolean getKeeper() {
        return keeper;
    }

    public void setKeeper(Boolean keeper) {
        this.keeper = keeper;
    }
}
