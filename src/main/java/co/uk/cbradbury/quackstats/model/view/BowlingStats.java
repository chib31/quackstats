package co.uk.cbradbury.quackstats.model.view;

import co.uk.cbradbury.quackstats.enums.MatchType;
import co.uk.cbradbury.quackstats.enums.ResultType;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Immutable
@Table(name = "bowling_stats")
public class BowlingStats {
    @Id
    private Long id;

    private Long teamId;

    private String playerName;

    private Integer bowlerNumber;

    private Integer deliveries;

    private Integer runs;

    private Integer wickets;

    private Integer maidens;

    private Integer noBalls;

    private Integer wides;

    private Integer hatTricks;

    private Date fixtureDate;

    private Integer season;

    private String opposition;

    @Enumerated(EnumType.STRING)
    private ResultType resultType;

    @Enumerated(EnumType.STRING)
    private MatchType matchType;

    private Integer overLength;

    private Integer sumVictimPosition;

    private Integer sumVictimRuns;

    private Integer wicketsBowled;

    private Integer wicketsLbw;

    private Integer wicketsCaught;

    private Integer wicketsStumped;

    public Long getId() {
        return id;
    }

    public Long getTeamId() {
        return teamId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public Integer getBowlerNumber() {
        return bowlerNumber;
    }

    public Integer getDeliveries() {
        return deliveries;
    }

    public Integer getRuns() {
        return runs;
    }

    public Integer getWickets() {
        return wickets;
    }

    public Integer getMaidens() {
        return maidens;
    }

    public Integer getNoBalls() {
        return noBalls;
    }

    public Integer getWides() {
        return wides;
    }

    public Integer getHatTricks() {
        return hatTricks;
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

    public String getMatchType() {
        return matchType.getValue();
    }

    public Integer getOverLength() {
        return overLength;
    }

    public Integer getSumVictimPosition() {
        return sumVictimPosition;
    }

    public Integer getSumVictimRuns() {
        return sumVictimRuns;
    }

    public Integer getWicketsBowled() {
        return wicketsBowled;
    }

    public Integer getWicketsLbw() {
        return wicketsLbw;
    }

    public Integer getWicketsCaught() {
        return wicketsCaught;
    }

    public Integer getWicketsStumped() {
        return wicketsStumped;
    }
}
