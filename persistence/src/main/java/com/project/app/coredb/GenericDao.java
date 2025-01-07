package com.project.app.coredb;

import com.project.app.dtos.Entity;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author Plamen
 * @param <T>
 */
public interface GenericDao<T extends Entity> {

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
    Optional<T> loadById(final Long id);

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
