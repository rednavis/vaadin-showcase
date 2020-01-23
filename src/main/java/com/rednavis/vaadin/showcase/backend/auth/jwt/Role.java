package com.rednavis.vaadin.showcase.backend.auth.jwt;

import java.util.Arrays;
import java.util.List;

/**
 * Enum of allowed user's roles.
 */
public enum Role {
  USER, ADMIN;

  public static List<Role> getRoles() {
    return Arrays.asList(Role.values());
  }
}
