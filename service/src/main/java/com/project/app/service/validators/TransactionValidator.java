package com.project.app.service.validators;

import com.project.app.service.exceptions.calcvalidation.InvalidTransactionStateException;
import com.project.app.entities.transaction.Transaction;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class TransactionValidator extends CrudValidator<Transaction> implements CalculationValidator<Transaction> {

    public TransactionValidator(Transaction transaction) {
        super(transaction);
    }

    public void validateAll(List<Transaction> transactions) {
        for (Transaction tx : transactions) {
            getValidator().setObject(tx);
            Predicate<Transaction> validation = t -> {
                return onCalc(t);
            };
            getValidator().validate(validation, new InvalidTransactionStateException());
        }
    }

    @Override
    public void onSave(Transaction entity) {
        getValidator().setObject(entity);
        getValidator().validate((tx) -> {
            return Objects.isNull(tx.getId()) && Objects.nonNull(tx.getPosition());
        },
                new NullPointerException());
    }

    @Override
    public void onCalculation(Transaction entity) {
        getValidator().setObject(entity);
        getValidator().validate(this::onCalc, new InvalidTransactionStateException());
    }

    @Override
    protected Long getEntityId(Transaction entity) {
        return entity.getId();
    }

    private boolean onCalc(Transaction tx) {
        boolean doesSignHaveValue = tx.getSign().getValue() != 0;
        boolean hasAmount = tx.getAmount() > 0;
        return doesSignHaveValue && hasAmount;
    }

}
