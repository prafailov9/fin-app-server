package com.project.app.entities.instrument;

import com.project.app.entities.instrument.frequency.Frequency;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;

import java.time.LocalDateTime;

@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class CreditInstrument extends DebtInstrument {
    
    private static final String TYPE = "credit";
    
    private Frequency principalFrequency;

    public CreditInstrument() {

    }

    public CreditInstrument(String instrumentName, LocalDateTime startOfPaymentPeriod,
            LocalDateTime endOfPaymentPeriod, double interestRate, Frequency interestFrequency, Frequency principalFrequency) {
        super(null, instrumentName, startOfPaymentPeriod, endOfPaymentPeriod, interestRate, interestFrequency);
        this.principalFrequency = principalFrequency;
    }

    public CreditInstrument(Long id, String instrumentName, LocalDateTime startOfPaymentPeriod,
            LocalDateTime endOfPaymentPeriod, double interestRate, Frequency interestFrequency, Frequency principalFrequency) {
        super(id, instrumentName, startOfPaymentPeriod, endOfPaymentPeriod, interestRate, interestFrequency);
        this.principalFrequency = principalFrequency;
    }

    public Frequency getPrincipalFrequency() {
        return principalFrequency;
    }

    public void setPrincipalFrequency(Frequency principalFrequency) {
        this.principalFrequency = principalFrequency;
    }

    @Override
    public String toString() {
        return "CreditInstrument{" + super.toString() + ", principalFrequency=" + principalFrequency + '}';
    }

    @Override
    public String getType() {
        return TYPE;
    }

}
