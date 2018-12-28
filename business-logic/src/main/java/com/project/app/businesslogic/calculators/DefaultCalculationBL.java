package com.project.app.businesslogic.calculators;

import com.project.app.businesslogic.results.CreditResultObject;
import com.project.app.businesslogic.results.DepositResultObject;
import com.project.app.businesslogic.results.ResultObject;
import com.project.app.entities.position.Position;

/**
 *
 * @author prafailov
 */
public class DefaultCalculationBL implements CalculationBL {

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
