package co.uk.cbradbury.quackstats.json;

import co.uk.cbradbury.quackstats.enums.BattingConclusion;
import co.uk.cbradbury.quackstats.enums.FieldingLocation;

public class BatJson {
    private Long squadMemberId;
    private Integer position;
    private Integer deliveries;
    private Integer runs;
    private Integer fours;
    private Integer sixes;
    private BattingConclusion battingConclusion;
    private Integer notOuts;
    private String wicketFielder;
    private String wicketBowler;
    private FieldingLocation whereCaught;

    public BatJson() {
    }

    public Long getSquadMemberId() {
        return squadMemberId;
    }

    public void setSquadMemberId(Long squadMemberId) {
        this.squadMemberId = squadMemberId;
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

    public Integer getNotOuts() {
        return notOuts;
    }

    public void setNotOuts(Integer notOuts) {
        this.notOuts = notOuts;
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
