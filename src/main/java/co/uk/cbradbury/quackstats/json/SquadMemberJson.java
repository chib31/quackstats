package co.uk.cbradbury.quackstats.json;

public class SquadMemberJson {
    private Long id;
    private Long playerId;
    private String name;
    private Boolean captain;
    private Boolean keeper;

    public SquadMemberJson() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getCaptain() {
        return captain;
    }

    public void setCaptain(Boolean captain) {
        this.captain = captain;
    }

    public Boolean getKeeper() {
        return keeper;
    }

    public void setKeeper(Boolean keeper) {
        this.keeper = keeper;
    }
}