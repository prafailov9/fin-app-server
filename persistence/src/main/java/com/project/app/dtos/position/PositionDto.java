package com.project.app.dtos.position;

import com.project.app.dtos.instrument.InstrumentDto;
import java.sql.Timestamp;

public class PositionDto {
    private Long id;
    private Timestamp dealStartingDate;
    private String payer;
    private String receiver;
    private double principal;
    private double positionVolume;

    private InstrumentDto instrument;

    public PositionDto() {
    }

    public PositionDto(Timestamp dealStartingDate, String payer, String receiver, double principal,
            double positionVolume, InstrumentDto instrument) {
        this.dealStartingDate = dealStartingDate;
        this.payer = payer;
        this.receiver = receiver;
        this.principal = principal;
        this.positionVolume = positionVolume;
        this.instrument = instrument;
    }

    public Long getId() {
        return id;
    }

    public Timestamp getDealStartingDate() {
        return dealStartingDate;
    }

    public String getPayer() {
        return payer;
    }

    public String getReceiver() {
        return receiver;
    }

    public double getPrincipal() {
        return principal;
    }

    public InstrumentDto getInstrument() {
        return instrument;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDealStartingDate(Timestamp dealStartingDate) {
        this.dealStartingDate = dealStartingDate;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setPrincipal(double principal) {
        this.principal = principal;
    }

    public void setInstrument(InstrumentDto instrument) {
        this.instrument = instrument;
    }

    public double getPositionVolume() {
        return positionVolume;
    }

    public void setPositionVolume(double positionVolume) {
        this.positionVolume = positionVolume;
    }

    // lists formatted data for sql queries. has additional check on if instrument reference exists.
    public String getDataAsString() {
        String positionString = toString();
        if (instrument == null || instrument.getId() == null) {
            return positionString;
        }

        positionString = String.format("%s, %s", positionString, instrument.getId());
        return positionString;
    }

    @Override
    public String toString() {
        return String.format("%s, '%s', '%s', '%s', %s, %s",
                id, dealStartingDate, payer, receiver, principal, positionVolume);
    }

}
