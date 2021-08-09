package co.uk.cbradbury.quackstats.json;

import co.uk.cbradbury.quackstats.enums.BattingConclusion;
import co.uk.cbradbury.quackstats.enums.FieldingLocation;

public class WicketJson {
    private Long bowlerSquadMemberId;
    private Long fielderSquadMemberId;
    private BattingConclusion battingConclusion;
    private Integer battingPosition;
    private Integer batterRuns;
    private FieldingLocation fieldingLocation;

    public WicketJson() {
    }

    public Long getBowlerSquadMemberId() {
        return bowlerSquadMemberId;
    }

    public void setBowlerSquadMemberId(Long bowlerSquadMemberId) {
        this.bowlerSquadMemberId = bowlerSquadMemberId;
    }

    public Long getFielderSquadMemberId() {
        return fielderSquadMemberId;
    }

    public void setFielderSquadMemberId(Long fielderSquadMemberId) {
        this.fielderSquadMemberId = fielderSquadMemberId;
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
