package co.uk.cbradbury.quackstats.json;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotNull;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
public class SquadMemberJson {

    @NotNull
    private String name;

    private Boolean captain;

    private Boolean keeper;

    public SquadMemberJson() {
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