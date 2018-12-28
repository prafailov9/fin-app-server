package com.project.app.entities.transaction;

import com.project.app.entities.jsonadapters.LocalDateTimeAdapter;
import com.project.app.entities.position.Position;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

public class Transaction {

    private Long id;

    @XmlElement(name = "transactionDate")
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime transactionDate;

    private double amount;

    // + = payer to receiver
    private Sign sign;

    private Position position;

    public Transaction() {

    }

    public Transaction(Long id, LocalDateTime transactionDate, double amount, Sign sign, Position position) {
        this.id = id;
        this.transactionDate = transactionDate;
        this.amount = amount;
        this.sign = sign;
        this.position = position;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public double getAmount() {
        return amount;
    }

    public Position getPosition() {
        return position;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Sign getSign() {
        return sign;
    }

    public void setSign(Sign sign) {
        this.sign = sign;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + Objects.hashCode(this.id);
        hash = 71 * hash + Objects.hashCode(this.transactionDate);
        hash = 71 * hash + (int) (Double.doubleToLongBits(this.amount) ^ (Double.doubleToLongBits(this.amount) >>> 32));
        hash = 71 * hash + Objects.hashCode(this.position);
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
        final Transaction other = (Transaction) obj;
        if (Double.doubleToLongBits(this.amount) != Double.doubleToLongBits(other.amount)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.transactionDate, other.transactionDate)) {
            return false;
        }
        if (!Objects.equals(this.position, other.position)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Transaction{" + "id=" + id + ", transactionDate=" + transactionDate + ", amount=" + amount + ", position=" + position + '}';
    }

}
