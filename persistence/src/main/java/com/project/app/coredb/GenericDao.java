package com.project.app.coredb;

import java.util.List;

/**
 *
 * @author Plamen
 * @param <T>
 */
public interface GenericDao<T> {

    /**
     *
     * @param entity
     */
    T save(final T entity);

    /**
     *
     * @param id
     * @return
     */
    T loadById(final Long id);

    /**
     *
     * @return
     */
    List<T> loadAll();

    /**
     *
     * @param entity
     */
    void delete(final T entity);

    /**
     *
     * @param entity
     */
    void update(final T entity);

}
