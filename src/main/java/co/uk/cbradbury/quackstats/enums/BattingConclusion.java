package co.uk.cbradbury.quackstats.enums;

public enum BattingConclusion {
    DNB("Did not bat", false, false),
    NOT_OUT("Not out", true, false),
    RETIRED("Retired", true, false),
    RETIRED_HURT("Retired hurt", true, false),
    BOWLED("Bowled", true, true),
    CAUGHT("Caught", true, true),
    HIT_TWICE("Hit ball twice", true, true),
    HIT_WICKET("Hit wicket", true, true),
    LBW("LBW", true, true),
    OBSTRUCTING("Obstructing", true, true),
    RUN_OUT("Run out", true, true),
    STUMPED("Stumped", true, true),
    TIMED_OUT("Timed out", true, true),
    OTHER("Other", true, true);

    private final String value;

    private final boolean isInnings;

    private final boolean isWicket;

    BattingConclusion(String value, boolean isInnings, boolean isWicket) {
        this.value = value;
        this.isInnings = isInnings;
        this.isWicket = isWicket;
    }

    public String getValue() {
        return value;
    }

    public boolean isInnings() {
        return isInnings;
    }

    public boolean isWicket() {
        return isWicket;
    }
}
