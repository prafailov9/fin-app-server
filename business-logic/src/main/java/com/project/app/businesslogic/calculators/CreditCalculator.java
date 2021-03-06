package com.project.app.businesslogic.calculators;

import com.project.app.businesslogic.dateadders.DateAdder;
import com.project.app.businesslogic.dateadders.DateAdderFactory;
import com.project.app.businesslogic.results.CreditResultObject;
import com.project.app.businesslogic.transaction.DefaultTransactionBL;
import com.project.app.businesslogic.transaction.TransactionBL;
import com.project.app.businesslogic.validators.CreditInstrumentValidator;
import com.project.app.businesslogic.validators.PositionValidator;
import com.project.app.businesslogic.validators.TransactionValidator;
import com.project.app.entities.instrument.CreditInstrument;
import com.project.app.entities.instrument.frequency.Frequency;
import com.project.app.entities.position.Position;
import com.project.app.entities.transaction.Transaction;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class CreditCalculator implements Calculator<CreditResultObject> {

    private final DateAdderFactory factory;
    private final TransactionBL tbl;
    private double debt;

    public CreditCalculator() {
        factory = new DateAdderFactory();
        tbl = new DefaultTransactionBL();
    }

    /*
        Method to calculate the interest and principal payments on given position.
     */
    @Override
    public CreditResultObject calculateCashFlow(Position position) {

        /*
            retreiving the data for calculations
         */
        CreditInstrument creditInstrument = (CreditInstrument) position.getInstrument();
        List<Transaction> transactions = tbl.getAllTransactionsByPosition(position);

        validateData(position, creditInstrument, transactions);

        /*
            calculating principal and interest payments and storing results in sorted maps.
         */
        Map<LocalDateTime, Double> prPayments = calculatePrincipalPayments(creditInstrument, transactions);
        Map<LocalDateTime, Double> intPayments = calculateInterestPayments(creditInstrument, transactions, prPayments);
        String instrumentType = "credit";

        /*
            Creating a Result object which contains the calculation results and other data;
         */
        CreditResultObject creditResultObject = new CreditResultObject(intPayments, position, instrumentType, prPayments);
        return creditResultObject;
    }

    @Override
    public double calculatePositionVolume(List<Transaction> transactions) {
        double volume = 0;
        for (Transaction tx : transactions) {
            volume += tx.getAmount() * tx.getSign().getValue();
        }
        return volume;
    }

    private double calculatePrincipal(double volume, int numberOfPayments) {
        double principal = volume / numberOfPayments;
        return principal;
    }

    public Map<LocalDateTime, Double> calculateInterestPayments(CreditInstrument instrument, List<Transaction> transactions,
            Map<LocalDateTime, Double> principalPayments) {

        LocalDateTime startDate = instrument.getStartOfPaymentPeriod();
        double interestRate = instrument.getInterestRate();
        Frequency interestFrequency = instrument.getInterestFrequency();
        int timeAmount = interestFrequency.getOrdinal();

        DateAdder adder = factory.getDateAdder(interestFrequency);
        LocalDateTime currentDate = startDate;

        Map<LocalDateTime, Double> interestPayments = new TreeMap<>();

        for (Entry<LocalDateTime, Double> entry : principalPayments.entrySet()) {
            LocalDateTime currPrDate = entry.getKey();
            while (currentDate.isBefore(currPrDate)) {
                double payment = interestRate * debt;
                currentDate = adder.addToDate(currentDate, timeAmount);
                interestPayments.put(currentDate, payment);
            }
            debt = entry.getValue();
        }
        return interestPayments;
    }

    public Map<LocalDateTime, Double> calculatePrincipalPayments(CreditInstrument instrument, List<Transaction> transactions) {
        LocalDateTime startDate = instrument.getStartOfPaymentPeriod();
        LocalDateTime endDate = instrument.getEndOfPaymentPeriod();
        Frequency principalFrequency = instrument.getPrincipalFrequency();
        double volume = calculatePositionVolume(transactions);
        debt = volume;
        int numberOfPrincipalPayments = calculateNumberOfPrincipalPayments(startDate, endDate, principalFrequency);

        double principal = calculatePrincipal(volume, numberOfPrincipalPayments);

        int timeAmount = principalFrequency.getOrdinal();
        double amount = volume;
        LocalDateTime currentDate = startDate;
        DateAdder adder = factory.getDateAdder(principalFrequency);
        Map<LocalDateTime, Double> principalPayments = new TreeMap<>();

        while (amount > 0) {
            amount = amount - principal;
            currentDate = adder.addToDate(currentDate, timeAmount);
            principalPayments.put(currentDate, amount);
        }
        return principalPayments;
    }

    private void validateData(Position pos, CreditInstrument ins, List<Transaction> txs) {

        PositionValidator posVal = new PositionValidator(pos);
        CreditInstrumentValidator instVal = new CreditInstrumentValidator(ins);
        TransactionValidator txVal = new TransactionValidator(txs.get(0));

        posVal.onCalculation(pos);
        instVal.onCalculation(ins);
        txVal.validateAll(txs);

    }

    private int calculateNumberOfPrincipalPayments(LocalDateTime startDate, LocalDateTime endDate, Frequency principalFrequency) {

        int timeAmount = principalFrequency.getOrdinal();
        DateAdder adder = factory.getDateAdder(principalFrequency);
        LocalDateTime currentDate = startDate;
        int counter = 0;
        while (currentDate.isBefore(endDate)) {
            currentDate = adder.addToDate(currentDate, timeAmount);
            counter++;
        }
        return counter;
    }

}
