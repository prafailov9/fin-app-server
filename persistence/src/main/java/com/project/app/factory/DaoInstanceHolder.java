package com.project.app.factory;

import com.project.app.coredb.GenericDao;
import com.project.app.daos.instrument.DefaultInstrumentDao;
import com.project.app.daos.instrument.InstrumentDao;
import com.project.app.daos.position.DefaultPositionDao;
import com.project.app.daos.position.PositionDao;
import com.project.app.daos.transaction.DefaultTransactionDao;
import com.project.app.daos.transaction.TransactionDao;
import com.project.app.dtos.Entity;
import com.project.app.exceptions.InvalidKeyException;

import java.util.concurrent.ConcurrentHashMap;

public final class DaoInstanceHolder {

    private static final ConcurrentHashMap<Class<? extends GenericDao<? extends Entity>>, GenericDao<? extends Entity>> DAO_CACHE;

    static {
        DAO_CACHE = new ConcurrentHashMap<>();

        DAO_CACHE.put(InstrumentDao.class, new DefaultInstrumentDao());
        DAO_CACHE.put(PositionDao.class, new DefaultPositionDao());
        DAO_CACHE.put(TransactionDao.class, new DefaultTransactionDao());
    }

    public static <T extends GenericDao<? extends Entity>> T get(final Class<T> daoType) {
        GenericDao<? extends Entity> dao = DAO_CACHE.get(daoType);
        if (dao == null) {
            throw new InvalidKeyException(daoType.getName());
        }
        return daoType.cast(dao);
    }
}

