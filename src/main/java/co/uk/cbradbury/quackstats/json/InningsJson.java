package co.uk.cbradbury.quackstats.json;

import java.util.Set;

public class InningsJson {
    private Integer inningsOrder;
    private Boolean teamIsBatting;
    private Integer deliveries;
    private Integer wickets;
    private Integer runs;
    private Integer byes;
    private Integer legByes;
    private Integer wides;
    private Integer noBalls;
    private Set<BatJson> batSet;
    private Set<BowlJson> bowlSet;
    private Set<WicketJson> wicketSet;

    public InningsJson() {
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

    public Set<BatJson> getBatSet() {
        return batSet;
    }

    public void setBatSet(Set<BatJson> batSet) {
        this.batSet = batSet;
    }

    public Set<BowlJson> getBowlSet() {
        return bowlSet;
    }

    public void setBowlSet(Set<BowlJson> bowlSet) {
        this.bowlSet = bowlSet;
    }

    public Set<WicketJson> getWicketSet() {
        return wicketSet;
    }

    public void setWicketSet(Set<WicketJson> wicketSet) {
        this.wicketSet = wicketSet;
    }
}
