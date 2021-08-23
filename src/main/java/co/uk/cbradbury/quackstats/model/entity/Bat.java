package co.uk.cbradbury.quackstats.model.entity;

import co.uk.cbradbury.quackstats.enums.BattingConclusion;
import co.uk.cbradbury.quackstats.enums.FieldingLocation;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.GenerationType.IDENTITY;

@Entity(name = "bat")
public class Bat {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @JsonIgnore
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "squad_member_id")
    @JsonIgnoreProperties(value = {"captain", "keeper", "player"})
    @NotNull
    private SquadMember squadMember;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "innings_id")
    @NotNull
    private Innings innings;

    @NotNull
    private Integer position;

    private Integer deliveries;

    @NotNull
    private Integer runs;

    private Integer fours;

    private Integer sixes;

    @Enumerated(EnumType.STRING)
    @NotNull
    private BattingConclusion battingConclusion;

    private String wicketFielder;

    private String wicketBowler;

    @Enumerated(EnumType.STRING)
    private FieldingLocation whereCaught;

    public Bat() {
    }

    public Long getId() {
        return id;
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

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Integer getDeliveries() {
        return deliveries;
    }

    public void setDeliveries(Integer deliveries) {
        this.deliveries = deliveries;
    }

    public Integer getRuns() {
        return runs;
    }

    public void setRuns(Integer runs) {
        this.runs = runs;
    }

    public Integer getFours() {
        return fours;
    }

    public void setFours(Integer fours) {
        this.fours = fours;
    }

    public Integer getSixes() {
        return sixes;
    }

    public void setSixes(Integer sixes) {
        this.sixes = sixes;
    }

    public BattingConclusion getBattingConclusion() {
        return battingConclusion;
    }

    public void setBattingConclusion(BattingConclusion battingConclusion) {
        this.battingConclusion = battingConclusion;
    }

    public String getWicketFielder() {
        return wicketFielder;
    }

    public void setWicketFielder(String wicketFielder) {
        this.wicketFielder = wicketFielder;
    }

    public String getWicketBowler() {
        return wicketBowler;
    }

    public void setWicketBowler(String wicketBowler) {
        this.wicketBowler = wicketBowler;
    }

    public FieldingLocation getWhereCaught() {
        return whereCaught;
    }

    public void setWhereCaught(FieldingLocation whereCaught) {
        this.whereCaught = whereCaught;
    }
}
