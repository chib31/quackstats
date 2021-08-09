package co.uk.cbradbury.quackstats.model.view;

import co.uk.cbradbury.quackstats.enums.MatchType;
import co.uk.cbradbury.quackstats.enums.ResultType;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Immutable
@Table(name = "fielding_stats")
public class FieldingStats {
    @Id
    private Long id;

    private Long teamId;

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

    @Enumerated(EnumType.STRING)
    private ResultType resultType;

    @Enumerated(EnumType.STRING)
    private MatchType matchType;

    private Integer overLength;

    public Long getId() {
        return id;
    }

    public Long getTeamId() {
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
