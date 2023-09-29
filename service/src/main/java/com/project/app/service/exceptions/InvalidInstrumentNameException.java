package com.project.app.service.exceptions;

public class InvalidInstrumentNameException extends EntityValidationException {

    private final static String MESSAGE = "Instrument name is Invalid!";

    public InvalidInstrumentNameException() {
        super();
    }

    @Override
    public String getMessage() {
        return MESSAGE;
    }

}
