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
"  WITH wicket_types AS (\n" +
"    SELECT w.fielder_id, w.batting_conclusion AS type, COUNT(*) total\n" +
"    FROM wicket w\n" +
"    WHERE w.fielder_id IS NOT NULL\n" +
"    GROUP BY w.fielder_id, w.batting_conclusion\n" +
"  ), wicket_totals AS (\n" +
"    SELECT\n" +
"      sm.id AS id,\n" +
"      COALESCE(sm.captain, false) AS captain,\n" +
"      COALESCE(sm.keeper, false) AS keeper,\n" +
"      COALESCE((SELECT total FROM wicket_types WHERE fielder_id = sm.id AND type = 'CAUGHT'), 0) AS catches,\n" +
"      COALESCE((SELECT total FROM wicket_types WHERE fielder_id = sm.id AND type = 'RUN_OUT'), 0) AS run_outs,\n" +
"      COALESCE((SELECT total FROM wicket_types WHERE fielder_id = sm.id AND type = 'STUMPED'), 0) AS stumpings,\n" +
"      COALESCE((SELECT COUNT(*) FROM wicket w WHERE fielder_id = sm.id), 0) wickets\n" +
"    FROM squad_member sm\n" +
"  )\n" +
"  SELECT\n" +
"    sm.id AS id,\n" +
"    s.team_id AS team_id,\n" +
"    p.scorecard_name AS player_name,\n" +
"    wt.captain,\n" +
"    wt.keeper,\n" +
"    wt.catches,\n" +
"    wt.run_outs,\n" +
"    wt.stumpings,\n" +
"    wt.wickets,\n" +
"    s.date AS fixture_date,\n" +
"    EXTRACT(year from s.date) AS season,\n" +
"    t.name AS opposition,\n" +
"    'vs ' || t.name || ' (' || s.date || ')' AS fixture,\n" +
"    s.result_type,\n" +
"    s.match_type,\n" +
"    s.over_length\n" +
"  FROM squad_member sm\n" +
"  JOIN wicket_totals wt ON sm.id = wt.id\n" +
"  JOIN player p ON sm.player_id = p.id\n" +
"  JOIN scorecard s on sm.scorecard_id = s.id\n" +
"  JOIN team t ON s.opponent_id = t.id"
)
public class FieldingStats {
    @Id
    private UUID id;

    private UUID teamId;

    private String playerName;

    private Boolean captain;

    private Boolean keeper;

    private Integer catches;

    private Integer runOuts;

    private Integer stumpings;

    private Integer wickets;

    private Date fixtureDate;

    private Integer season;

    private String opposition;

    private String fixture;

    @Enumerated(EnumType.STRING)
    private ResultType resultType;

    @Enumerated(EnumType.STRING)
    private MatchType matchType;

    private Integer overLength;

    public UUID getId() {
        return id;
    }

    public UUID getTeamId() {
        return teamId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public Boolean getCaptain() {
        return captain;
    }

    public Boolean getKeeper() {
        return keeper;
    }

    public Integer getCatches() {
        return catches;
    }

    public Integer getRunOuts() {
        return runOuts;
    }

    public Integer getStumpings() {
        return stumpings;
    }

    public Integer getWickets() {
        return wickets;
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

    public String getFixture() {
        return fixture;
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
}
