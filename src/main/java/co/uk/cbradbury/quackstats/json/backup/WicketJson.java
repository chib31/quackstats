package co.uk.cbradbury.quackstats.json.backup;

import co.uk.cbradbury.quackstats.enums.BattingConclusion;
import co.uk.cbradbury.quackstats.enums.FieldingLocation;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class WicketJson {

    @NotNull
    private String bowlerName;

    @NotNull
    private String fielderName;

    @NotNull
    private BattingConclusion battingConclusion;

    private Integer battingPosition;

    private Integer batterRuns;

    private FieldingLocation fieldingLocation;

    public WicketJson() {
    }

    public String getBowlerName() {
        return bowlerName;
    }

    public void setBowlerName(String bowlerName) {
        this.bowlerName = bowlerName;
    }

    public String getFielderName() {
        return fielderName;
    }

    public void setFielderName(String fielderName) {
        this.fielderName = fielderName;
    }

    public BattingConclusion getBattingConclusion() {
        return battingConclusion;
    }

    public void setBattingConclusion(BattingConclusion battingConclusion) {
        this.battingConclusion = battingConclusion;
    }

    public Integer getBattingPosition() {
        return battingPosition;
    }

    public void setBattingPosition(Integer battingPosition) {
        this.battingPosition = battingPosition;
    }

    public Integer getBatterRuns() {
        return batterRuns;
    }

    public void setBatterRuns(Integer batterRuns) {
        this.batterRuns = batterRuns;
    }

    public FieldingLocation getFieldingLocation() {
        return fieldingLocation;
    }

    public void setFieldingLocation(FieldingLocation fieldingLocation) {
        this.fieldingLocation = fieldingLocation;
    }
}
