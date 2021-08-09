package co.uk.cbradbury.quackstats.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;
import java.util.UUID;

import static javax.persistence.GenerationType.IDENTITY;

@Entity(name = "player")
public class Player {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @JsonValue
    private final UUID uuid = UUID.randomUUID();

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "main_team_id")
    private Team mainTeam;

    private String firstName;

    private String middleNames;

    private String lastName;

    private String preferredName;

    private String scorecardName;

    private Date membershipStart;

    private Integer capNumber;

    private Integer shirtNumber;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "player")
    private Set<SquadMember> squadMemberSet;

    public Player() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Team getMainTeam() {
        return mainTeam;
    }

    public void setMainTeam(Team mainTeam) {
        this.mainTeam = mainTeam;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleNames() {
        return middleNames;
    }

    public void setMiddleNames(String middleNames) {
        this.middleNames = middleNames;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPreferredName() {
        return preferredName;
    }

    public void setPreferredName(String preferredName) {
        this.preferredName = preferredName;
    }

    public String getScorecardName() {
        return scorecardName;
    }

    public void setScorecardName(String scorecardName) {
        this.scorecardName = scorecardName;
    }

    public Date getMembershipStart() {
        return membershipStart;
    }

    public void setMembershipStart(Date membershipStart) {
        this.membershipStart = membershipStart;
    }

    public Integer getCapNumber() {
        return capNumber;
    }

    public void setCapNumber(Integer capNumber) {
        this.capNumber = capNumber;
    }

    public Integer getShirtNumber() {
        return shirtNumber;
    }

    public void setShirtNumber(Integer shirtNumber) {
        this.shirtNumber = shirtNumber;
    }

    public Set<SquadMember> getSquadMemberSet() {
        return squadMemberSet;
    }

    public void setSquadMemberSet(Set<SquadMember> squadMemberSet) {
        this.squadMemberSet = squadMemberSet;
    }
}
