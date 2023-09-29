package com.project.app.service.calculators;

import com.project.app.service.dateadders.DateAdder;
import com.project.app.service.dateadders.DateAdderFactory;
import com.project.app.service.results.DepositResultObject;
import com.project.app.service.transaction.DefaultTransactionBL;
import com.project.app.service.transaction.TransactionBL;
import com.project.app.service.validators.DepositInstrumentValidator;
import com.project.app.service.validators.PositionValidator;
import com.project.app.service.validators.TransactionValidator;
import com.project.app.entities.instrument.DepositInstrument;
import com.project.app.entities.instrument.frequency.Frequency;
import com.project.app.entities.position.Position;
import com.project.app.entities.transaction.Transaction;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class DepositCalculator implements Calculator<DepositResultObject> {

    private DateAdder dateAdder;
    private final DateAdderFactory factory;
    private final TransactionBL tbl;

    public DepositCalculator() {
        factory = new DateAdderFactory();
        tbl = new DefaultTransactionBL();
    }

    @Override
    public DepositResultObject calculateCashFlow(Position pos) {
        DepositInstrument dep = (DepositInstrument) pos.getInstrument();
        List<Transaction> txs = tbl.getAllTransactionsByPosition(pos);

        validateData(pos, dep, txs);

        LocalDateTime startDate = dep.getStartOfPaymentPeriod();
        LocalDateTime endDate = dep.getEndOfPaymentPeriod();
        double positionVolume = calculatePositionVolume(txs);
        pos.setPositionVolume(positionVolume);

        double interestRate = dep.getInterestRate();
        Frequency interestFrequency = dep.getInterestFrequency();

        double amount = positionVolume;
        LocalDateTime currentDate = startDate;
        dateAdder = factory.getDateAdder(interestFrequency);
        Map<LocalDateTime, Double> interestPayments = new TreeMap<>();

        while (currentDate.isBefore(endDate)) {
            amount += interestRate * amount;
            int timeAmount = interestFrequency.getOrdinal();
            currentDate = dateAdder.addToDate(currentDate, timeAmount);
            interestPayments.put(currentDate, amount);
        }

        String instrumentType = "deposit";
        DepositResultObject dro = new DepositResultObject(interestPayments, pos, instrumentType);

        return dro;
    }

    private void validateData(Position pos, DepositInstrument dep, List<Transaction> txs) {
        PositionValidator posVal = new PositionValidator(pos);
        DepositInstrumentValidator depVal = new DepositInstrumentValidator(dep);
        TransactionValidator txVal = new TransactionValidator(txs.get(0));

        posVal.onCalculation(pos);
        depVal.onCalculation(dep);
        txVal.validateAll(txs);

    }

    @Override
    public double calculatePositionVolume(List<Transaction> txs) {
        double volume = 0;
        for (Transaction tx : txs) {
            volume += tx.getAmount() * tx.getSign().getValue();
        }
        return volume;
    }

}
