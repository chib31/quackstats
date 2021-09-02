package co.uk.cbradbury.quackstats.model.entity;

import javax.persistence.*;
import java.util.UUID;

@Entity(name = "innings")
public class Innings {

    @Id
    private final UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scorecard_id")
    private Scorecard scorecard;

    private Integer inningsOrder;

    private Boolean teamIsBatting;

    private Integer deliveries;

    private Integer wickets;

    private Integer runs;

    private Integer byes;

    private Integer legByes;

    private Integer wides;

    private Integer noBalls;

    private Integer penaltyRuns;

    public Innings() {
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public Scorecard getScorecard() {
        return scorecard;
    }

    public void setScorecard(Scorecard scorecard) {
        this.scorecard = scorecard;
    }

    public Integer getInningsOrder() {
        return inningsOrder;
    }

    public void setInningsOrder(Integer inningsOrder) {
        this.inningsOrder = inningsOrder;
    }

    public Boolean getTeamIsBatting() {
        return teamIsBatting;
    }

    public void setTeamIsBatting(Boolean teamIsBatting) {
        this.teamIsBatting = teamIsBatting;
    }

    public Integer getDeliveries() {
        return deliveries;
    }

    public void setDeliveries(Integer deliveries) {
        this.deliveries = deliveries;
    }

    public Integer getWickets() {
        return wickets;
    }

    public void setWickets(Integer wickets) {
        this.wickets = wickets;
    }

    public Integer getRuns() {
        return runs;
    }

    public void setRuns(Integer runs) {
        this.runs = runs;
    }

    public Integer getByes() {
        return byes;
    }

    public void setByes(Integer byes) {
        this.byes = byes;
    }

    public Integer getLegByes() {
        return legByes;
    }

    public void setLegByes(Integer legByes) {
        this.legByes = legByes;
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

    public Integer getPenaltyRuns() {
        return penaltyRuns;
    }

    public void setPenaltyRuns(Integer penaltyRuns) {
        this.penaltyRuns = penaltyRuns;
    }
}
