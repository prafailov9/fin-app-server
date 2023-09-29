package com.project.app.service.results;

import com.project.app.service.adapters.JsonMapAdapter;
import com.project.app.entities.position.Position;
import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.time.LocalDateTime;
import java.util.Map;

@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({CreditResultObject.class, DepositResultObject.class})
public abstract class ResultObject {

    @XmlElement(name = "interestPayments")
    @XmlJavaTypeAdapter(JsonMapAdapter.class)
    private Map<LocalDateTime, Double> interestPayments;

    private Position position;

    private String instrumentType;

    public ResultObject() {

    }

    public ResultObject(Map<LocalDateTime, Double> interestPayments, Position position, String instrumentType) {
        this.interestPayments = interestPayments;
        this.position = position;
        this.instrumentType = instrumentType;

    }

    public Position getPosition() {
        return position;
    }

    public String getInstrumentType() {
        return instrumentType;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setInstrumentType(String instrumentType) {
        this.instrumentType = instrumentType;
    }

    public Map<LocalDateTime, Double> getInterestPayments() {
        return interestPayments;
    }

    public void setInterestPayments(Map<LocalDateTime, Double> interestPayments) {
        this.interestPayments = interestPayments;
    }

    @Override
    public String toString() {
        return "ResultObject{" + "interestPayments=" + interestPayments.toString() + ',' + ", position=" + position + ", instrumentType=" + instrumentType + '}';
    }

}
