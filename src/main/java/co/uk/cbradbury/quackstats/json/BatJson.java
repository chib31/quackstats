package co.uk.cbradbury.quackstats.json;

import co.uk.cbradbury.quackstats.enums.BattingConclusion;
import co.uk.cbradbury.quackstats.enums.FieldingLocation;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotNull;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
public class BatJson {

    @NotNull
    private String name;

    @NotNull
    private Integer position;

    private Integer deliveries;

    @NotNull
    private Integer runs;

    private Integer fours;

    private Integer sixes;

    @NotNull
    private BattingConclusion battingConclusion;

    private String wicketFielder;

    private String wicketBowler;

    private FieldingLocation whereCaught;

    public BatJson() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
