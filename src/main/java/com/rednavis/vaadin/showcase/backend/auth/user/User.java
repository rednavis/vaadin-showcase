package com.rednavis.vaadin.showcase.backend.auth.user;

import com.rednavis.vaadin.showcase.backend.auth.jwt.Role;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User entity.
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@RequestScoped
public class User implements Serializable {

  private String id;

  private String userName;
  private String firstName;
  private String lastName;
  private String password;
  private List<Role> roles;

  boolean isActive;

}
