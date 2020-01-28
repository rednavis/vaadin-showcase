package com.rednavis.vaadin.showcase.backend.auth.exception;

/**
 * AuthenticationException provides information about exception during authentication.
 */
public class AuthenticationException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public AuthenticationException(String message) {
    super(message);
  }

  public AuthenticationException(String message, Throwable throwable) {
    super(message, throwable);
  }
}
