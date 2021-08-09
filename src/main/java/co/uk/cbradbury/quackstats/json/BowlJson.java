package co.uk.cbradbury.quackstats.json;

public class BowlJson {
    private Long SquadMemberId;
    private Integer bowlerNumber;
    private Integer deliveries;
    private Integer maidens;
    private Integer runs;
    private Integer wides;
    private Integer noBalls;
    private Integer hatTricks;

    public BowlJson() {
    }

    public Long getSquadMemberId() {
        return SquadMemberId;
    }

    public void setSquadMemberId(Long squadMemberId) {
        SquadMemberId = squadMemberId;
    }

    public Integer getBowlerNumber() {
        return bowlerNumber;
    }

    public void setBowlerNumber(Integer bowlerNumber) {
        this.bowlerNumber = bowlerNumber;
    }

    public Integer getDeliveries() {
        return deliveries;
    }

    public void setDeliveries(Integer deliveries) {
        this.deliveries = deliveries;
    }

    public Integer getMaidens() {
        return maidens;
    }

    public void setMaidens(Integer maidens) {
        this.maidens = maidens;
    }

    public Integer getRuns() {
        return runs;
    }

    public void setRuns(Integer runs) {
        this.runs = runs;
    }

    public Integer getWides() {
        return wides;
    }

    public void setWides(Integer wides) {
        this.wides = wides;
    }

    public Integer getNoBalls() {
        return noBalls;
    }

    public void setNoBalls(Integer noBalls) {
        this.noBalls = noBalls;
    }

    public Integer getHatTricks() {
        return hatTricks;
    }

    public void setHatTricks(Integer hatTricks) {
        this.hatTricks = hatTricks;
    }
}
