package com.rednavis.vaadin.showcase.backend.auth.exception;

/**
 * AccessDeniedException provides information about exception during authorisation.
 */
public class AccessDeniedException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public AccessDeniedException(String message) {
    super(message);
  }

  public AccessDeniedException(String message, Throwable throwable) {
    super(message, throwable);
  }
}
