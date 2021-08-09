package co.uk.cbradbury.quackstats.enums;

public enum ResultType {
    WON("Won"),
    LOST("Lost"),
    DRAWN("Drawn"),
    TIED("Tied"),
    NA("N/A");

    private final String value;

    ResultType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
