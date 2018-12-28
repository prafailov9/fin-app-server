package com.project.app.businesslogic.exceptions.calcvalidation;

/**
 *
 * @author prafailov
 */
public class InvalidInstrumentStateBeforeCalculationException extends RuntimeException {
    
    private final static String MESSAGE = "Invalid instrument state! Dates and/or interestRate might have invalid values.";
    
    public InvalidInstrumentStateBeforeCalculationException() {
        super();
    }
    
    @Override
    public String getMessage() {
        return MESSAGE;
    }
    
}
