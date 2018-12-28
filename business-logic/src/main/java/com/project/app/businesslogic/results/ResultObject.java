package com.project.app.businesslogic.results;

import com.project.app.businesslogic.adapters.JsonMapAdapter;
import com.project.app.entities.position.Position;
import java.time.LocalDateTime;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

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
