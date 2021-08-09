package co.uk.cbradbury.quackstats.model.view;

import co.uk.cbradbury.quackstats.enums.BattingConclusion;
import co.uk.cbradbury.quackstats.enums.MatchType;
import co.uk.cbradbury.quackstats.enums.ResultType;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Immutable
@Table(name = "batting_stats")
public class BattingStats {
    @Id
    private Long id;

    private Long teamId;

    private String playerName;

    private Integer runs;

    private Integer deliveries;

    private Integer fours;

    private Integer sixes;

    @Enumerated(EnumType.STRING)
    private BattingConclusion battingConclusion;

    @Transient
    private Integer wickets;

    @Transient
    private Integer innings;

    @Transient
    private Integer notOuts;

    private Integer position;

    private Date fixtureDate;

    private Integer season;

    private String opposition;

    @Enumerated(EnumType.STRING)
    private ResultType resultType;

    private Integer teamTotal;

    @Enumerated(EnumType.STRING)
    private MatchType matchType;

    private Integer overLength;

    public BattingStats() {
    }

    public Long getId() {
        return id;
    }

    public Long getTeamId() {
        return teamId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public Integer getRuns() {
        return runs;
    }

    public Integer getDeliveries() {
        return deliveries;
    }

    public Integer getFours() {
        return fours;
    }

    public Integer getSixes() {
        return sixes;
    }

    public String getBattingConclusion() {
        return battingConclusion.getValue();
    }

    public Integer getNotOuts() {
        return (battingConclusion.isInnings() && !battingConclusion.isWicket()) ? 1 : 0;
    }

    public Integer getWickets() {
        return battingConclusion.isWicket() ? 1 : 0;
    }

    public Integer getInnings() {
        return battingConclusion.isInnings() ? 1 : 0;
    }

    public Integer getPosition() {
        return position;
    }

    public Date getFixtureDate() {
        return fixtureDate;
    }

    public Integer getSeason() {
        return season;
    }

    public String getOpposition() {
        return opposition;
    }

    public String getResultType() {
        return resultType.getValue();
    }

    public Integer getTeamTotal() {
        return teamTotal;
    }

    public String getMatchType() {
        return matchType.getValue();
    }

    public Integer getOverLength() {
        return overLength;
    }
}
