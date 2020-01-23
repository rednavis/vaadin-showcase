package com.rednavis.vaadin.showcase.backend.auth.jwt;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Headers used for communicating JWT tokens
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtHeader {
  public static final String AUTHORIZATION_TOKEN = "Authorization";
}
