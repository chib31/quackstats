package co.uk.cbradbury.quackstats.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity(name = "squad_member")
public class SquadMember {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id")
    private Player player;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scorecard_id")
    private Scorecard scorecard;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean captain;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean keeper;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
