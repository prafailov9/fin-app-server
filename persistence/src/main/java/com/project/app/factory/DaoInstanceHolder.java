package com.project.app.factory;

import com.project.app.coredb.GenericDao;
import com.project.app.daos.instrument.DefaultInstrumentDao;
import com.project.app.daos.position.DefaultPositionDao;
import com.project.app.daos.transaction.DefaultTransactionDao;
import com.project.app.dtos.Entity;
import com.project.app.exceptions.InvalidKeyException;

import java.util.concurrent.ConcurrentHashMap;

public final class DaoInstanceHolder {

    private static final ConcurrentHashMap<String, GenericDao<? extends Entity>> DAO_CACHE = new ConcurrentHashMap<>();

    static {
        DAO_CACHE.put("instrument", new DefaultInstrumentDao());
        DAO_CACHE.put("position", new DefaultPositionDao());
        DAO_CACHE.put("transaction", new DefaultTransactionDao());
    }

    private DaoInstanceHolder() {

    }

    public static GenericDao<? extends Entity> get(final String key) {
        GenericDao<? extends Entity> dao = DAO_CACHE.get(key);
        if (dao == null) {
            throw new InvalidKeyException(key);
        }
        return dao;
    }

}
