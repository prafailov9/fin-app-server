package com.project.app.businesslogic.calculators;

import com.project.app.businesslogic.results.ResultObject;
import com.project.app.entities.position.Position;

/**
 *
 * @author prafailov
 */
public interface CalculationBL {

    ResultObject getDepositCalculationResults(Position position);

    ResultObject getCreditCalculationResults(Position position);

}
