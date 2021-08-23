package co.uk.cbradbury.quackstats.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity(name = "innings")
public class Innings {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @JsonIgnore
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "innings", cascade = CascadeType.PERSIST)
    private List<Bat> batList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "innings", cascade = CascadeType.PERSIST)
    private List<Bowl> bowlList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "innings", cascade = CascadeType.PERSIST)
    private List<Wicket> wicketList;

    public Innings() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<Bat> getBatList() {
        return batList;
    }

    public void setBatList(List<Bat> batList) {
        this.batList = batList;
    }

    public List<Bowl> getBowlList() {
        return bowlList;
    }

    public void setBowlList(List<Bowl> bowlList) {
        this.bowlList = bowlList;
    }

    public List<Wicket> getWicketList() {
        return wicketList;
    }

    public void setWicketList(List<Wicket> wicketList) {
        this.wicketList = wicketList;
    }
}
