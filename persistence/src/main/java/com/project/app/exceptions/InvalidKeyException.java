package com.project.app.exceptions;

public class InvalidKeyException extends RuntimeException {

    public InvalidKeyException(String key) {
        super(String.format("provided key %s doesn't correspond to an existing DAO", key));
    }

}
