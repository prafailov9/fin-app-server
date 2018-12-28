package com.project.app.entities.instrument;

import com.project.app.entities.instrument.frequency.Frequency;
import java.time.LocalDateTime;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class DepositInstrument extends DebtInstrument {
    
    private static final String TYPE = "deposit";
    
    public DepositInstrument() {
        
    }
    
    public DepositInstrument(String instrumentName, LocalDateTime startOfPaymentPeriod, 
            LocalDateTime endOfPaymentPeriod, double interestRate, Frequency interestFrequency) {
        super(null, instrumentName, startOfPaymentPeriod, endOfPaymentPeriod, interestRate, interestFrequency);
    }
    
    public DepositInstrument(Long id, String instrumentName, LocalDateTime startOfPaymentPeriod, 
            LocalDateTime endOfPaymentPeriod, double interestRate, Frequency interestFrequency) {
        super(id, instrumentName, startOfPaymentPeriod, endOfPaymentPeriod, interestRate, interestFrequency);
    }


    @Override
    public String toString() {
        return "DepositInstrument{" + super.toString() + '}';
    }

    @Override
    public String getType() {
        return TYPE;
    }

}
