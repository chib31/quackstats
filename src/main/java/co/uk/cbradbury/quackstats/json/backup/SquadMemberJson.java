package co.uk.cbradbury.quackstats.json.backup;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SquadMemberJson {

    @NotNull
    private final String name;

    private final Boolean captain;

    private final Boolean keeper;

    public SquadMemberJson(String name, Boolean captain, Boolean keeper) {
        this.name = name;
        this.captain = captain;
        this.keeper = keeper;
    }

    public String getName() {
        return name;
    }

    public Boolean getCaptain() {
        return captain;
    }

    public Boolean getKeeper() {
        return keeper;
    }
}