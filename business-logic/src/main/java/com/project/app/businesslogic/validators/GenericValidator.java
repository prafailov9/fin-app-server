package com.project.app.businesslogic.validators;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/**
 *
 * @author prafailov
 * @param <T>
 */
public class GenericValidator<T> {

    private T object;
    private List<Throwable> exceptions;

    private GenericValidator(T object) {
        this.object = object;
        exceptions = new ArrayList<>();
    }

    public static <T> GenericValidator<T> of(T object) {
        return new GenericValidator<>(Objects.requireNonNull(object));
    }

    public void validate(Predicate<T> validation, Throwable thr) {
        if (!validation.test(object)) {
            exceptions.add(thr);
        }
    }
    
    public void setObject(T object) {
        this.object = Objects.requireNonNull(object);
    }
    
    public T getObject() {
        return object;
    }
    
//    public <U> Validator<T> validate(Function<T, U> projection, Predicate<U> validation,
//            Throwable thr) {
//        return validate(projection.andThen(validation::test)::apply, thr);
//    }
//    public T get() throws IllegalStateException {
//        if (exceptions.isEmpty()) {
//            return object;
//        }
//        IllegalStateException e = new IllegalStateException();
//        exceptions.forEach(e::addSuppressed);
//        throw e;
//    }
    public List<Throwable> getExceptions() {
        return exceptions;
    }


}
