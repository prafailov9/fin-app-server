package com.project.app.service.validators;

import java.util.Objects;

/**
 *
 * @author prafailov
 */
public abstract class CrudValidator<T> {

    private final GenericValidator<T> validator;

    public CrudValidator(T entity) {
        validator = GenericValidator.of(entity);
    }

    public GenericValidator<T> getValidator() {
        return this.validator;
    }

    public abstract void onSave(T entity);

    public void onDelete(T entity) {
        validator.setObject(entity);
        makePersistenceCheck();
    }

    public void onUpdate(T entity) {
        validator.setObject(entity);
        makePersistenceCheck();
    }

    public void onGet(T entity) {
        validator.setObject(entity);
        makePersistenceCheck();
    }

    protected void makePersistenceCheck() {
        Long id = getEntityId(validator.getObject());
        validator.validate((T t) -> Objects.isNull(id), new NullPointerException());
    }

    protected abstract Long getEntityId(T entity);

}
