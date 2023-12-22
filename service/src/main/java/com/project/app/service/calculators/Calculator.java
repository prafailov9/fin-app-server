package com.project.app.service.calculators;

import com.project.app.entities.position.Position;
import com.project.app.entities.transaction.Transaction;
import com.project.app.service.results.ResultObject;

import java.util.List;

public interface Calculator<T extends ResultObject> {

    ResultObject calculateCashFlow(Position position);

    double calculatePositionVolume(List<Transaction> transactions);

}
