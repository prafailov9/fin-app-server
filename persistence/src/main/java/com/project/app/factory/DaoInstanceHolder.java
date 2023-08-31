package com.project.app.factory;

import com.project.app.coredb.GenericDao;
import com.project.app.daos.instrument.DefaultInstrumentDao;
import com.project.app.daos.position.DefaultPositionDao;
import com.project.app.daos.transaction.DefaultTransactionDao;
import com.project.app.dtos.Entity;
import com.project.app.exceptions.InvalidKeyException;

import java.util.concurrent.ConcurrentHashMap;

public final class DaoInstanceHolder {

    private static final ConcurrentHashMap<String, GenericDao<? extends Entity>> daoCache = new ConcurrentHashMap<>();

    static {
        daoCache.put("instrument", new DefaultInstrumentDao());
        daoCache.put("position", new DefaultPositionDao());
        daoCache.put("transaction", new DefaultTransactionDao());
    }

    public static GenericDao<? extends Entity> get(final String key) {
        GenericDao<? extends Entity> dao = daoCache.get(key);
        if (dao == null) {
            throw new InvalidKeyException(key);
        }
        return dao;
    }

}
