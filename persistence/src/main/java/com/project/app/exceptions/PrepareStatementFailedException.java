package com.project.app.exceptions;

public class PrepareStatementFailedException extends RuntimeException {

  public PrepareStatementFailedException(String msg, Throwable cause) {
    super(msg, cause);
  }

}
