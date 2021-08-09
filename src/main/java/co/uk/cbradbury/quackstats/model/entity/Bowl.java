package co.uk.cbradbury.quackstats.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity(name = "bowl")
public class Bowl {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @JsonIgnore
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "squad_member_id")
    @JsonIgnoreProperties(value = {"captain", "keeper", "player"})
    private SquadMember squadMember;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "innings_id")
    private Innings innings;

    private Integer bowlerNumber;

    private Integer deliveries;

    private Integer maidens;

    private Integer runs;

    private Integer wickets;

    private Integer wides;

    private Integer noBalls;

    private Integer hatTricks;

    public Bowl() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SquadMember getSquadMember() {
        return squadMember;
    }

    public void setSquadMember(SquadMember squadMember) {
        this.squadMember = squadMember;
    }

    public Innings getInnings() {
        return innings;
    }

    public void setInnings(Innings innings) {
        this.innings = innings;
    }

    public Integer getBowlerNumber() {
        return bowlerNumber;
    }

    public void setBowlerNumber(Integer bowlerNumber) {
        this.bowlerNumber = bowlerNumber;
    }

    public Integer getDeliveries() {
        return deliveries;
    }

    public void setDeliveries(Integer deliveries) {
        this.deliveries = deliveries;
    }

    public Integer getMaidens() {
        return maidens;
    }

    public void setMaidens(Integer maidens) {
        this.maidens = maidens;
    }

    public Integer getRuns() {
        return runs;
    }

    public void setRuns(Integer runs) {
        this.runs = runs;
    }

    public Integer getWickets() {
        return wickets;
    }

    public void setWickets(Integer wickets) {
        this.wickets = wickets;
    }

    public Integer getWides() {
        return wides;
    }

    public void setWides(Integer wides) {
        this.wides = wides;
    }

    public Integer getNoBalls() {
        return noBalls;
    }

    public void setNoBalls(Integer noBalls) {
        this.noBalls = noBalls;
    }

    public Integer getHatTricks() {
        return hatTricks;
    }

    public void setHatTricks(Integer hatTricks) {
        this.hatTricks = hatTricks;
    }
}
