package co.uk.cbradbury.quackstats.model.entity;

import co.uk.cbradbury.quackstats.enums.MatchType;
import co.uk.cbradbury.quackstats.enums.RecordType;
import co.uk.cbradbury.quackstats.enums.ResultType;
import co.uk.cbradbury.quackstats.enums.ScorecardStatus;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity(name = "scorecard")
public class Scorecard {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RecordType recordType;

    @Enumerated(EnumType.STRING)
    private ScorecardStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "opponent_id")
    private Team opponent;

    private Date date;

    private Integer fixtureOrder;

    private String location;

    @Enumerated(EnumType.STRING)
    private MatchType matchType;

    private Integer inningsLength;

    private Integer overLength;

    private Boolean wonToss;

    private Boolean batFirst;

    private Integer wicketRating;

    @Enumerated(EnumType.STRING)
    private ResultType resultType;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "scorecard", cascade = CascadeType.PERSIST)
    private List<SquadMember> squadMemberList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "scorecard", cascade = CascadeType.PERSIST)
    private List<Innings> inningsList;

    public Scorecard() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RecordType getRecordType() {
        return recordType;
    }

    public void setRecordType(RecordType recordType) {
        this.recordType = recordType;
    }

    public ScorecardStatus getStatus() {
        return status;
    }

    public void setStatus(ScorecardStatus status) {
        this.status = status;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Team getOpponent() {
        return opponent;
    }

    public void setOpponent(Team opponent) {
        this.opponent = opponent;
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

    public Integer getWicketRating() {
        return wicketRating;
    }

    public void setWicketRating(Integer wicketRating) {
        this.wicketRating = wicketRating;
    }

    public ResultType getResultType() {
        return resultType;
    }

    public void setResultType(ResultType resultType) {
        this.resultType = resultType;
    }

    public List<SquadMember> getSquadMemberList() {
        return squadMemberList;
    }

    public void setSquadMemberList(List<SquadMember> squadMemberList) {
        this.squadMemberList = squadMemberList;
    }

    public List<Innings> getInningsList() {
        return inningsList;
    }

    public void setInningsList(List<Innings> inningsList) {
        this.inningsList = inningsList;
    }
}
