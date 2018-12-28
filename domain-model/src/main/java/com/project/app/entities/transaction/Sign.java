package com.project.app.entities.transaction;

/**
 *
 * @author prafailov
 */
public enum Sign {

    POSITIVE(1), NEGATIVE(-1);
    private int value;

    Sign(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static Sign valueOf(int value) {
        if (value == 1) {
            return Sign.POSITIVE;
        } else if (value == -1) {
            return Sign.NEGATIVE;
        }
        throw new NullPointerException();
    }

}
