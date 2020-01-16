package com.rednavis.vaadin.showcase.exception;

public class InitConfigError extends RuntimeException {

  public InitConfigError() {
  }

  public InitConfigError(String message) {
    super(message);
  }

  public InitConfigError(String message, Throwable throwable) {
    super(message, throwable);
  }

}
