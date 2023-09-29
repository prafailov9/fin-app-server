package com.project.app.service.calculators;

import com.project.app.service.results.CreditResultObject;
import com.project.app.service.results.DepositResultObject;
import com.project.app.service.results.ResultObject;
import com.project.app.entities.position.Position;

/**
 *
 * @author prafailov
 */
public class DefaultCalculationService implements CalculationService {

    @Override
    public ResultObject getDepositCalculationResults(Position position) {
        Calculator<DepositResultObject> depositCalculator = new DepositCalculator();
        ResultObject dro = depositCalculator.calculateCashFlow(position);

        return dro;
    }

    @Override
    public ResultObject getCreditCalculationResults(Position position) {
        Calculator<CreditResultObject> creditCalculator = new CreditCalculator();
        ResultObject ro = creditCalculator.calculateCashFlow(position);
        return ro;
    }

}
