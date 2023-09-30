package com.project.app.service.calculators;

import com.project.app.service.Service;
import com.project.app.service.results.ResultObject;
import com.project.app.entities.position.Position;

/**
 *
 * @author prafailov
 */
public interface CalculationService extends Service {

    ResultObject getDepositCalculationResults(Position position);

    ResultObject getCreditCalculationResults(Position position);

}
