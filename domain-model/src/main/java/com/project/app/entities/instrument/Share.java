package com.project.app.entities.instrument;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;

import java.time.LocalDateTime;

/**
 *
 * @author Plamen
 */
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class Share extends Instrument {
    
    private static final String TYPE = "share";
    
    public Share() {

    }

    public Share(Long id, String instrumentName, LocalDateTime startOfPaymentPeriod, LocalDateTime endOfPaymentPeriod) {
        super(id, instrumentName, startOfPaymentPeriod, endOfPaymentPeriod);
    }

    public Share(String instrumentName, LocalDateTime startOfPaymentPeriod, LocalDateTime endOfPaymentPeriod) {
        super(null, instrumentName, startOfPaymentPeriod, endOfPaymentPeriod);
    }

    @Override
    public String toString() {
        return "Share{" + super.toString() + '}';
    }

    @Override
    public String getType() {
        return TYPE;
    }

}
