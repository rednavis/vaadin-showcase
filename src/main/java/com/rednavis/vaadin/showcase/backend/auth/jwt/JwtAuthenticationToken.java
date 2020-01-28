package com.rednavis.vaadin.showcase.backend.auth.jwt;

import java.io.Serializable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Kate Novik
 */
@RequiredArgsConstructor
public class JwtAuthenticationToken implements Serializable {

  @Getter
  private final Principal _principal;

}
