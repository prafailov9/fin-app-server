package com.project.app.service.calculators;

import com.project.app.entities.instrument.DepositInstrument;
import com.project.app.entities.instrument.frequency.Frequency;
import com.project.app.entities.position.Position;
import com.project.app.entities.transaction.Transaction;
import com.project.app.service.ServiceInstanceHolder;
import com.project.app.service.dateadders.DateAdder;
import com.project.app.service.dateadders.DateAdderInstanceHolder;
import com.project.app.service.results.DepositResultObject;
import com.project.app.service.transaction.TransactionService;
import com.project.app.service.validators.DepositInstrumentValidator;
import com.project.app.service.validators.PositionValidator;
import com.project.app.service.validators.TransactionValidator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class DepositCalculator implements Calculator<DepositResultObject> {

    private final TransactionService transactionService;

    public DepositCalculator() {
        transactionService = ServiceInstanceHolder.get(TransactionService.class);
    }

    @Override
    public DepositResultObject calculateCashFlow(Position pos) {
        DepositInstrument dep = (DepositInstrument) pos.getInstrument();
        List<Transaction> txs = transactionService.getAllTransactionsByPosition(pos);

        validateData(pos, dep, txs);

        LocalDateTime startDate = dep.getStartOfPaymentPeriod();
        LocalDateTime endDate = dep.getEndOfPaymentPeriod();
        double positionVolume = calculatePositionVolume(txs);
        pos.setPositionVolume(positionVolume);

        double interestRate = dep.getInterestRate();
        Frequency interestFrequency = dep.getInterestFrequency();

        double amount = positionVolume;
        LocalDateTime currentDate = startDate;
        DateAdder dateAdder = DateAdderInstanceHolder.getDateAdder(interestFrequency);
        Map<LocalDateTime, Double> interestPayments = new TreeMap<>();

        while (currentDate.isBefore(endDate)) {
            amount += interestRate * amount;
            int timeAmount = interestFrequency.getOrdinal();
            currentDate = dateAdder.addToDate(currentDate, timeAmount);
            interestPayments.put(currentDate, amount);
        }

        return new DepositResultObject(interestPayments, pos, dep.getType());
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
