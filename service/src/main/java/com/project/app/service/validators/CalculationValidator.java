package com.project.app.service.validators;

/**
 *
 * @author prafailov
 * @param <T>
 */
public interface CalculationValidator<T> {

    void onCalculation(T entity);

}
