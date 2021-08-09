package co.uk.cbradbury.quackstats.json;

import co.uk.cbradbury.quackstats.enums.MatchType;
import co.uk.cbradbury.quackstats.enums.RecordType;
import co.uk.cbradbury.quackstats.enums.ResultType;
import co.uk.cbradbury.quackstats.enums.ScorecardStatus;
import co.uk.cbradbury.quackstats.model.entity.*;

import javax.persistence.Enumerated;
import java.sql.Date;
import java.util.List;
import java.util.Set;

public class FullScorecardJson {
    private Long scorecardId;

    private RecordType recordType;

    private ScorecardStatus scorecardStatus;

    private Long teamId;

    private Long opponentId;

    private Date date;

    private Integer fixtureOrder;

    private String location;

    @Enumerated
    private MatchType matchType;

    private Integer inningsLength;

    private Integer overLength;

    private Boolean wonToss;

    private Boolean batFirst;

    @Enumerated
    private ResultType resultType;

    private Set<SquadMember> squadMemberSet;

    private Set<Innings> inningsSet;

    private List<Bat> batList;

    private List<Bowl> bowlList;

    private List<Wicket> wicketList;

    public FullScorecardJson() {
    }

    public Long getScorecardId() {
        return scorecardId;
    }

    public void setScorecardId(Long scorecardId) {
        this.scorecardId = scorecardId;
    }

    public RecordType getRecordType() {
        return recordType;
    }

    public void setRecordType(RecordType recordType) {
        this.recordType = recordType;
    }

    public ScorecardStatus getScorecardStatus() {
        return scorecardStatus;
    }

    public void setScorecardStatus(ScorecardStatus scorecardStatus) {
        this.scorecardStatus = scorecardStatus;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public Long getOpponentId() {
        return opponentId;
    }

    public void setOpponentId(Long opponentId) {
        this.opponentId = opponentId;
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

    public Set<SquadMember> getSquadMemberSet() {
        return squadMemberSet;
    }

    public void setSquadMemberSet(Set<SquadMember> squadMemberSet) {
        this.squadMemberSet = squadMemberSet;
    }

    public Set<Innings> getInningsSet() {
        return inningsSet;
    }

    public void setInningsSet(Set<Innings> inningsSet) {
        this.inningsSet = inningsSet;
    }

    public List<Bat> getBatList() {
        return batList;
    }

    public void setBatList(List<Bat> batList) {
        this.batList = batList;
    }

    public List<Bowl> getBowlList() {
        return bowlList;
    }

    public void setBowlList(List<Bowl> bowlList) {
        this.bowlList = bowlList;
    }

    public List<Wicket> getWicketList() {
        return wicketList;
    }

    public void setWicketList(List<Wicket> wicketList) {
        this.wicketList = wicketList;
    }
}
