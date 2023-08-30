package com.project.app.entities.instrument;

import com.project.app.entities.jsonadapters.LocalDateTimeAdapter;
import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.time.LocalDateTime;
import java.util.Objects;

@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({CreditInstrument.class, DepositInstrument.class, Share.class})
public abstract class Instrument {

    private Long id;

    private String instrumentName;

    @XmlElement(name = "startOfPaymentPeriod")
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime startOfPaymentPeriod;

    @XmlElement(name = "endOfPaymentPeriod")
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime endOfPaymentPeriod;

    public Instrument() {

    }

    public Instrument(Long id, String instrumentName, LocalDateTime startOfPaymentPeriod,
            LocalDateTime endOfPaymentPeriod) {
        this.id = id;
        this.instrumentName = instrumentName;
        this.startOfPaymentPeriod = startOfPaymentPeriod;
        this.endOfPaymentPeriod = endOfPaymentPeriod;
    }
    
    abstract public String getType();
    
    public Long getId() {
        return id;
    }

    public String getInstrumentName() {
        return instrumentName;
    }

    public LocalDateTime getStartOfPaymentPeriod() {
        return startOfPaymentPeriod;
    }

    public LocalDateTime getEndOfPaymentPeriod() {
        return endOfPaymentPeriod;
    }

    public void setInstrumentName(String instrumentName) {
        this.instrumentName = instrumentName;
    }

    public void setStartOfPaymentPeriod(LocalDateTime startOfPaymentPeriod) {
        this.startOfPaymentPeriod = startOfPaymentPeriod;
    }

    public void setEndOfPaymentPeriod(LocalDateTime endOfPaymentPeriod) {
        this.endOfPaymentPeriod = endOfPaymentPeriod;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
        hash = 97 * hash + Objects.hashCode(this.instrumentName);
        hash = 97 * hash + Objects.hashCode(this.startOfPaymentPeriod);
        hash = 97 * hash + Objects.hashCode(this.endOfPaymentPeriod);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Instrument other = (Instrument) obj;
        if (!Objects.equals(this.instrumentName, other.instrumentName)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.startOfPaymentPeriod, other.startOfPaymentPeriod)) {
            return false;
        }
        if (!Objects.equals(this.endOfPaymentPeriod, other.endOfPaymentPeriod)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "id=" + id + ", instrumentName=" + instrumentName + ", startOfPaymentPeriod=" + startOfPaymentPeriod + ", endOfPaymentPeriod=" + endOfPaymentPeriod;
    }

}
