package com.project.app.entities.instrument;

import com.project.app.entities.instrument.frequency.Frequency;
import java.time.LocalDateTime;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

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
