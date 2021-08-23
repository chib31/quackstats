package co.uk.cbradbury.quackstats.json.backup;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotNull;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class InningsJson {

    @NotNull
    private Integer inningsOrder;

    @NotNull
    private Boolean teamIsBatting;

    @NotNull
    private Integer deliveries;

    @NotNull
    private Integer wickets;

    @NotNull
    private Integer runs;

    @NotNull
    private Integer byes;

    @NotNull
    private Integer legByes;

    @NotNull
    private Integer wides;

    @NotNull
    private Integer noBalls;

    @NotNull
    private Integer penaltyRuns;

    private List<BatJson> batList;

    private List<BowlJson> bowlList;

    private List<WicketJson> wicketList;

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

    public List<BatJson> getBatList() {
        return batList;
    }

    public void setBatList(List<BatJson> batList) {
        this.batList = batList;
    }

    public List<BowlJson> getBowlList() {
        return bowlList;
    }

    public void setBowlList(List<BowlJson> bowlList) {
        this.bowlList = bowlList;
    }

    public List<WicketJson> getWicketList() {
        return wicketList;
    }

    public void setWicketList(List<WicketJson> wicketList) {
        this.wicketList = wicketList;
    }
}
