package com.project.app.dtos.transaction;

import com.project.app.dtos.Entity;
import com.project.app.dtos.position.PositionDto;

import java.sql.Timestamp;

public class TransactionDto implements Entity {

    private Long id;
    private double amount;
    private int sign;
    private Timestamp transactionDate;

    private PositionDto position;

    public TransactionDto() {
    }

    public TransactionDto(Timestamp transactionDate, double amount, int sign, PositionDto position) {
        this.transactionDate = transactionDate;
        this.amount = amount;
        this.sign = sign;
        this.position = position;
    }

    public Long getId() {
        return id;
    }

    public Timestamp getTransactionDate() {
        return transactionDate;
    }

    public double getAmount() {
        return amount;
    }

    public PositionDto getPosition() {
        return position;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTransactionDate(Timestamp transactionDate) {
        this.transactionDate = transactionDate;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setPosition(PositionDto position) {
        this.position = position;
    }

    public int getSign() {
        return sign;
    }

    public void setSign(int sign) {
        this.sign = sign;
    }

    public String getDataAsString() {
        String txString = toString();
        if (position == null || position.getId() == null) {
            return txString;
        }

        txString = String.format("%s, %s", txString, position.getId());
        return txString;
    }

    @Override
    public String toString() {
        return String.format("%s, %s, %s, '%s'", id, amount, sign, transactionDate);
    }

}
