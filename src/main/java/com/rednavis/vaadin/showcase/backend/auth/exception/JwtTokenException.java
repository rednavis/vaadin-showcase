package com.rednavis.vaadin.showcase.backend.auth.exception;

/**
 * JwtTokenException provides information about exception during working with Jwt token.
 */
public  class JwtTokenException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public JwtTokenException(String message) {
    super(message);
  }

  public JwtTokenException(String message, Throwable throwable) {
    super(message, throwable);
  }

}
