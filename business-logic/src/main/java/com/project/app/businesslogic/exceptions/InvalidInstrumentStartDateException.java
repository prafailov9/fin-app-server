package com.project.app.businesslogic.exceptions;

public class InvalidInstrumentStartDateException extends EntityValidationException {

    private final static String MESSAGE = "Invalid sarting date! It's value cannot be equal to or after the end date.";

    public InvalidInstrumentStartDateException() {
        super();
    }

    @Override
    public String getMessage() {
        return MESSAGE;
    }

}
