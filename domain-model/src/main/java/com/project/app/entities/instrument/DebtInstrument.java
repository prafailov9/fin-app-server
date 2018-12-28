package com.project.app.entities.instrument;

import com.project.app.entities.instrument.frequency.Frequency;
import java.time.LocalDateTime;

public abstract class DebtInstrument extends Instrument {

    private double interestRate;
    private Frequency interestFrequency;

    public DebtInstrument() {
        
    }
    
    public DebtInstrument(Long id, String instrumentName, LocalDateTime startOfPaymentPeriod,
            LocalDateTime endOfPaymentPeriod, double interestRate, Frequency interestFrequency) {
        super(id, instrumentName, startOfPaymentPeriod, endOfPaymentPeriod);
        this.interestRate = interestRate;
        this.interestFrequency = interestFrequency;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public Frequency getInterestFrequency() {
        return interestFrequency;
    }

    public void setInterestFrequency(Frequency interestFrequency) {
        this.interestFrequency = interestFrequency;
    }

    @Override
    public String toString() {
        return ", " + interestRate + ", " + interestFrequency;
    }

}
