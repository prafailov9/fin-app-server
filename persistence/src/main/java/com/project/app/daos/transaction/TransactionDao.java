package com.project.app.daos.transaction;

import com.project.app.coredb.GenericDao;
import com.project.app.dtos.transaction.TransactionDto;
import java.util.List;

public interface TransactionDao extends GenericDao<TransactionDto> {

    List<TransactionDto> loadAllByReference(Long fk);

}
