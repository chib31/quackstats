package co.uk.cbradbury.quackstats.enums;

public enum MatchType {
    LIMITED_OVERS("Limited overs"),
    TIMED("Timed"),
    TEST("Test"),
    OTHER("Other");

    private final String value;

    MatchType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
