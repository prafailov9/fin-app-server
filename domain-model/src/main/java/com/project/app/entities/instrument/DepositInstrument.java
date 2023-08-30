package com.project.app.entities.instrument;

import com.project.app.entities.instrument.frequency.Frequency;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;

import java.time.LocalDateTime;

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
