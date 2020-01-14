package com.rednavis.vaadin.showcase.exception;

public class UnauthorizedError extends RuntimeException {

  public UnauthorizedError() {
  }

  public UnauthorizedError(String message) {
    super(message);
  }

  public UnauthorizedError(String message, Throwable throwable) {
    super(message, throwable);
  }

}
