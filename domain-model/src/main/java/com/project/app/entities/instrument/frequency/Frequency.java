package com.project.app.entities.instrument.frequency;

public enum Frequency {

    DAILY("daily", 1, 1),
    WEEKLY("weekly", 1, 2),
    MONTHLY("monthly", 1, 3),
    QUATERLY("quaterly", 3, 4),
    SEMI_ANNUALLY("semi-annually", 2, 5),
    ANNUALLY("annually", 1, 6);

    private String freq;
    private int ordinal;
    private int value;

    Frequency(String freq, int ordinal, int value) {
        this.freq = freq;
        this.ordinal = ordinal;
        this.value = value;
    }

    public String getFreq() {
        return freq;
    }

    public void setFreq(String freq) {
        this.freq = freq;
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
