package com.rednavis.vaadin.showcase.backend.auth.jwt;

import java.util.List;
import javax.enterprise.context.RequestScoped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Security context holds information about username and roles for certain request.
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@RequestScoped
public class SecurityContext {

  private String userName;
  private List<Role> roles;

  public void clear() {
    this.userName = null;
    this.roles = null;
  }

}
