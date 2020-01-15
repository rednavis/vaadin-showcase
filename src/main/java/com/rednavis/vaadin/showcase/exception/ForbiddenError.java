package com.rednavis.vaadin.showcase.exception;

public class ForbiddenError extends RuntimeException {

  public ForbiddenError() {
  }

  public ForbiddenError(String message) {
    super(message);
  }

  public ForbiddenError(String message, Throwable throwable) {
    super(message, throwable);
  }

}
