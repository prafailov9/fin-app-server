package com.project.app.service.results;

import com.project.app.entities.position.Position;
import java.time.LocalDateTime;
import java.util.Map;

/**
 *
 * @author prafailov
 */
public class DepositResultObject extends ResultObject {

    public DepositResultObject() {

    }

    public DepositResultObject(Map<LocalDateTime, Double> cashFlow, Position position, String instrumentType) {
        super(cashFlow, position, instrumentType);
    }

}
