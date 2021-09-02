package co.uk.cbradbury.quackstats.model.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.UUID;

@Entity(name = "player")
public class Player {

    @Id
    private UUID id;

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

    public Player() {
        this.id = UUID.randomUUID();
    }

    public Player(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
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
}
