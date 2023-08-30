package com.project.app.exceptions;

public class DatabaseInitializationException extends RuntimeException {

  public DatabaseInitializationException(Throwable ex) {
    super(ex.getMessage(), ex.getCause());
  }

}
