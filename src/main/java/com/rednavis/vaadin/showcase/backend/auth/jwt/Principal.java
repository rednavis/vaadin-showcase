package com.rednavis.vaadin.showcase.backend.auth.jwt;

import java.util.List;
import javax.enterprise.context.RequestScoped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Base class with user's authentication data.
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@RequestScoped
public class Principal {

  private String userName;
  private String firstName;
  private String lastName;
  private List<Role> roles;

}
