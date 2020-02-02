package com.rednavis.vaadin.showcase.backend.auth.jwt;

import javax.enterprise.context.RequestScoped;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User dto for getting authentication information.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequestScoped
public class UserDetail {
  private String username;
  private String password;
}
