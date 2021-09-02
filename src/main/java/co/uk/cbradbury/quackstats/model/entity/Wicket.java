package co.uk.cbradbury.quackstats.model.entity;

import co.uk.cbradbury.quackstats.enums.BattingConclusion;
import co.uk.cbradbury.quackstats.enums.FieldingLocation;

import javax.persistence.*;
import java.util.UUID;

@Entity(name = "wicket")
public class Wicket {

    @Id
    private final UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "innings_id")
    private Innings innings;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fielder_id")
    private SquadMember fielder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bowler_id")
    private SquadMember bowler;

    @Enumerated(EnumType.STRING)
    private BattingConclusion battingConclusion;

    private Integer battingPosition;

    private Integer batterRuns;

    @Enumerated(EnumType.STRING)
    private FieldingLocation fieldingLocation;

    public Wicket() {
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public Innings getInnings() {
        return innings;
    }

    public void setInnings(Innings innings) {
        this.innings = innings;
    }

    public SquadMember getFielder() {
        return fielder;
    }

    public void setFielder(SquadMember fielder) {
        this.fielder = fielder;
    }

    public SquadMember getBowler() {
        return bowler;
    }

    public void setBowler(SquadMember bowler) {
        this.bowler = bowler;
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
