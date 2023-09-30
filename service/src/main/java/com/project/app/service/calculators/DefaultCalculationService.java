package com.project.app.service.calculators;

import com.project.app.entities.position.Position;
import com.project.app.service.results.CreditResultObject;
import com.project.app.service.results.DepositResultObject;
import com.project.app.service.results.ResultObject;

/**
 *
 * @author prafailov
 */
public class DefaultCalculationService implements CalculationService {

    @Override
    public ResultObject getDepositCalculationResults(Position position) {
        Calculator<DepositResultObject> depositCalculator = new DepositCalculator();

        return depositCalculator.calculateCashFlow(position);
    }

    @Override
    public ResultObject getCreditCalculationResults(Position position) {
        Calculator<CreditResultObject> creditCalculator = new CreditCalculator();
        return creditCalculator.calculateCashFlow(position);
    }

}
