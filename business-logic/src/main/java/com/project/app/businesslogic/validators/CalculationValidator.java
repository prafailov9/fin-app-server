package com.project.app.businesslogic.validators;

/**
 *
 * @author prafailov
 * @param <T>
 */
public interface CalculationValidator<T> {

    void onCalculation(T entity);

}
