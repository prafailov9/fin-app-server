package com.project.app.entities.instrument.frequency;

public enum Frequency {

    DAILY("daily", 1, 1),
    WEEKLY("weekly", 1, 2),
    MONTHLY("monthly", 1, 3),
    QUARTERLY("quarterly", 3, 4),
    SEMI_ANNUALLY("semi-annually", 2, 5),
    ANNUALLY("annually", 1, 6);

    private String name;
    private int ordinal;
    private int value;

    Frequency(String name, int ordinal, int value) {
        this.name = name;
        this.ordinal = ordinal;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

}
