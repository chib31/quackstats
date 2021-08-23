package co.uk.cbradbury.quackstats.model.view;

import co.uk.cbradbury.quackstats.enums.BattingConclusion;
import co.uk.cbradbury.quackstats.enums.MatchType;
import co.uk.cbradbury.quackstats.enums.ResultType;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Immutable
@Subselect(
"  SELECT\n" +
"    b.id,\n" +
"    s.team_id AS team_id,\n" +
"    p.scorecard_name AS player_name,\n" +
"    b.runs,\n" +
"    b.deliveries,\n" +
"    b.fours,\n" +
"    b.sixes,\n" +
"    b.batting_conclusion,\n" +
"    b.position,\n" +
"    s.date AS fixture_date,\n" +
"    EXTRACT(year from s.date) AS season,\n" +
"    o.name AS opposition,\n" +
"    s.result_type,\n" +
"    i.runs AS team_total,\n" +
"    s.match_type,\n" +
"    s.over_length\n" +
"  FROM bat b\n" +
"  JOIN innings i on b.innings_id = i.id\n" +
"  JOIN squad_member sm on b.squad_member_id = sm.id\n" +
"  JOIN player p on sm.player_id = p.id\n" +
"  JOIN scorecard s on i.scorecard_id = s.id\n" +
"  JOIN team o on s.opponent_id = o.id"
)
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
        return battingConclusion.getDescription();
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
