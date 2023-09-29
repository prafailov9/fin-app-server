package com.project.app.service.calculators;

import com.project.app.service.results.ResultObject;
import com.project.app.entities.position.Position;

/**
 *
 * @author prafailov
 */
public interface CalculationBL {

    ResultObject getDepositCalculationResults(Position position);

    ResultObject getCreditCalculationResults(Position position);

}
