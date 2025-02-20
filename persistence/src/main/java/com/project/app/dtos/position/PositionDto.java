package com.project.app.dtos.position;

import com.project.app.dtos.Entity;
import com.project.app.dtos.instrument.InstrumentDto;

import java.sql.Timestamp;

import static java.lang.String.format;

public class PositionDto implements Entity {
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

    /**
     * Lists formatted data for sql queries. has additional check if instrument reference exists.
     *
     * @return
     */
    public String getDataAsString() {
        return (instrument == null || instrument.getId() == null)
                ? toString()
                : format("%s, %s", this, instrument.getId());
    }

    @Override
    public String toString() {
        return format("%s, '%s', '%s', '%s', %s, %s",
                id, dealStartingDate, payer, receiver, principal, positionVolume);
    }

}
