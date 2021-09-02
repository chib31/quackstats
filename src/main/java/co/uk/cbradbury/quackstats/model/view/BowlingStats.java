package co.uk.cbradbury.quackstats.model.view;

import co.uk.cbradbury.quackstats.enums.MatchType;
import co.uk.cbradbury.quackstats.enums.ResultType;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import javax.persistence.*;
import java.sql.Date;
import java.util.UUID;

@Entity
@Immutable
@Subselect(
"  WITH wicket_agg AS (\n" +
"    SELECT\n" +
"      w.bowler_id AS bowler_sm_id,\n" +
"      SUM(w.batting_position) AS sum_victim_position,\n" +
"      SUM(w.batter_runs) AS sum_victim_runs,\n" +
"      COUNT(*) FILTER (WHERE w.batting_conclusion = 'BOWLED') AS wickets_bowled,\n" +
"      COUNT(*) FILTER (WHERE w.batting_conclusion = 'LBW') AS wickets_lbw,\n" +
"      COUNT(*) FILTER (WHERE w.batting_conclusion = 'CAUGHT') AS wickets_caught,\n" +
"      COUNT(*) FILTER (WHERE w.batting_conclusion = 'STUMPED') AS wickets_stumped\n" +
"    FROM wicket w\n" +
"    GROUP BY w.bowler_id\n" +
"  )\n" +
"  SELECT\n" +
"    b.id,\n" +
"    s.team_id AS team_id,\n" +
"    p.scorecard_name AS player_name,\n" +
"    b.bowler_number,\n" +
"    b.deliveries,\n" +
"    b.runs,\n" +
"    b.wickets,\n" +
"    b.maidens,\n" +
"    b.no_balls,\n" +
"    b.wides,\n" +
"    b.hat_tricks,\n" +
"    s.date AS fixture_date,\n" +
"    EXTRACT(year from s.date) AS season,\n" +
"    o.name AS opposition,\n" +
"    s.result_type,\n" +
"    i.runs AS team_total,\n" +
"    s.match_type,\n" +
"    s.over_length,\n" +
"    w.sum_victim_position,\n" +
"    w.sum_victim_runs,\n" +
"    w.wickets_bowled,\n" +
"    w.wickets_lbw,\n" +
"    w.wickets_caught,\n" +
"    w.wickets_stumped\n" +
"  FROM bowl b\n" +
"  JOIN innings i on b.innings_id = i.id\n" +
"  JOIN squad_member sm on b.squad_member_id = sm.id\n" +
"  JOIN player p on sm.player_id = p.id\n" +
"  JOIN scorecard s on i.scorecard_id = s.id\n" +
"  JOIN team o on s.opponent_id = o.id\n" +
"  LEFT JOIN wicket_agg w ON w.bowler_sm_id = sm.id"
)
public class BowlingStats {
    @Id
    private UUID id;

    private UUID teamId;

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

    public UUID getId() {
        return id;
    }

    public UUID getTeamId() {
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
