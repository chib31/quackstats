package co.uk.cbradbury.quackstats.json;

import co.uk.cbradbury.quackstats.enums.MatchType;
import co.uk.cbradbury.quackstats.enums.ResultType;
import co.uk.cbradbury.quackstats.enums.ScorecardStatus;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.List;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
public class ScorecardJson {

    @NotNull
    private UUID teamId;

    @NotNull
    private ScorecardStatus scorecardStatus;

    /* This needs to be a name rather than a ID to allow spreadsheet imports */
    @NotNull
    private String opponentName;
    
    @NotNull
    private Date date;

    @NotNull
    private Integer fixtureOrder;

    private String location;

    @NotNull
    private MatchType matchType;

    private Integer inningsLength;

    private Integer overLength;

    private Boolean wonToss;

    @NotNull
    private Boolean batFirst;

    @NotNull
    private ResultType resultType;

    @Valid
    private List<SquadMemberJson> squadMemberList;

    private List<InningsJson> inningsList;

    public UUID getTeamId() {
        return teamId;
    }

    public void setTeamId(UUID teamId) {
        this.teamId = teamId;
    }

    public ScorecardStatus getScorecardStatus() {
        return scorecardStatus;
    }

    public void setScorecardStatus(ScorecardStatus scorecardStatus) {
        this.scorecardStatus = scorecardStatus;
    }

    public String getOpponentName() {
        return opponentName;
    }

    public void setOpponentName(String opponentName) {
        this.opponentName = opponentName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getFixtureOrder() {
        return fixtureOrder;
    }

    public void setFixtureOrder(Integer fixtureOrder) {
        this.fixtureOrder = fixtureOrder;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public MatchType getMatchType() {
        return matchType;
    }

    public void setMatchType(MatchType matchType) {
        this.matchType = matchType;
    }

    public Integer getInningsLength() {
        return inningsLength;
    }

    public void setInningsLength(Integer inningsLength) {
        this.inningsLength = inningsLength;
    }

    public Integer getOverLength() {
        return overLength;
    }

    public void setOverLength(Integer overLength) {
        this.overLength = overLength;
    }

    public Boolean getWonToss() {
        return wonToss;
    }

    public void setWonToss(Boolean wonToss) {
        this.wonToss = wonToss;
    }

    public Boolean getBatFirst() {
        return batFirst;
    }

    public void setBatFirst(Boolean batFirst) {
        this.batFirst = batFirst;
    }

    public ResultType getResultType() {
        return resultType;
    }

    public void setResultType(ResultType resultType) {
        this.resultType = resultType;
    }

    public List<SquadMemberJson> getSquadMemberList() {
        return squadMemberList;
    }

    public void setSquadMemberList(List<SquadMemberJson> squadMemberList) {
        this.squadMemberList = squadMemberList;
    }

    public List<InningsJson> getInningsList() {
        return inningsList;
    }

    public void setInningsList(List<InningsJson> inningsList) {
        this.inningsList = inningsList;
    }
}
