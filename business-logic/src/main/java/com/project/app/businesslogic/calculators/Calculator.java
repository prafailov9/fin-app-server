package com.project.app.businesslogic.calculators;

import com.project.app.businesslogic.results.ResultObject;
import com.project.app.entities.position.Position;
import com.project.app.entities.transaction.Transaction;
import java.util.List;

public interface Calculator<T extends ResultObject> {

    ResultObject calculateCashFlow(Position position);

    double calculatePositionVolume(List<Transaction> transactions);

}
