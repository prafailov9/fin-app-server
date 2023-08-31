package com.project.app.dtos.instrument;

import com.project.app.dtos.Entity;

import java.sql.Timestamp;

public class InstrumentDto implements Entity {

    private Long id;
    private String instrumentName;
    private double interestRate;

    private Timestamp paymentStartingDate;
    private Timestamp paymentEndingDate;
    private String interestFrequency;
    private String principalFrequency;

    private String instrumentType;

    public InstrumentDto() {
    }


    public InstrumentDto(String instrumentName, double interestRate, Timestamp paymentStartingDate,
            Timestamp paymentEndingDate, String interestFrequency,
            String principalFrequency, String intrumentType) {
        this.instrumentName = instrumentName;
        this.interestRate = interestRate;
        this.interestFrequency = interestFrequency;
        this.principalFrequency = principalFrequency;
        this.paymentStartingDate = paymentStartingDate;
        this.paymentEndingDate = paymentEndingDate;
        this.instrumentType = intrumentType;
    }

    public Long getId() {
        return id;
    }

    public String getInstrumentName() {
        return instrumentName;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public String getInterestFrequency() {
        return interestFrequency;
    }

    public String getPrincipalFrequency() {
        return principalFrequency;
    }

    public Timestamp getPaymentStartingDate() {
        return paymentStartingDate;
    }

    public Timestamp getPaymentEndingDate() {
        return paymentEndingDate;
    }

    public String getIntrumentType() {
        return instrumentType;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setInstrumentName(String instrumentName) {
        this.instrumentName = instrumentName;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public void setInterestFrequency(String interestFrequency) {
        this.interestFrequency = interestFrequency;
    }

    public void setPrincipalFrequency(String principalFrequency) {
        this.principalFrequency = principalFrequency;
    }

    public void setPaymentStartingDate(Timestamp paymentStartingDate) {
        this.paymentStartingDate = paymentStartingDate;
    }

    public void setPaymentEndingDate(Timestamp paymentEndingDate) {
        this.paymentEndingDate = paymentEndingDate;
    }

    public void setInstrumentType(String instrumentType) {
        this.instrumentType = instrumentType;
    }

    @Override
    public String toString() {
        return id + ", '" + instrumentName + "', " + interestRate + ", '" + paymentStartingDate + "', '"
                + paymentEndingDate + "', '" + interestFrequency + "', '"
                + principalFrequency + "', '" + instrumentType + "'";
    }

}
