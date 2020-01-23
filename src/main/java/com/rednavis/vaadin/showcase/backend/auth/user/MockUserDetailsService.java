package com.rednavis.vaadin.showcase.backend.auth.user;

import com.rednavis.vaadin.showcase.backend.auth.exception.AuthenticationException;
import com.rednavis.vaadin.showcase.backend.auth.jwt.JwtService;
import com.rednavis.vaadin.showcase.backend.auth.jwt.Principal;
import com.rednavis.vaadin.showcase.backend.auth.jwt.Role;
import java.util.Collections;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * This implementation searches for User entities by the e-mail address
 * supplied in the login screen.
 */
@ApplicationScoped
public class MockUserDetailsService {

  @Inject
  private JwtService jwtService;

    /**
     * Recovers user from the database using the e-mail address supplied
     * in the login screen. If the user is found, returns a
     * {@link User}.
     *
     * @param username User's e-mail address
     *
     */
    public User loadUserByUsername(String username) {
      if ("admin@rednavis.com".equals(username)) {
        return User.builder()
            .userName(username)
            .password("admin")
            .roles(Collections.singletonList(Role.ADMIN))
            .build();
      }
      return User.builder()
          .userName(username)
          .password("user")
          .roles(Collections.singletonList(Role.USER))
          .build();
    }

  public String authenticate(String username, String password) {
    User user = loadUserByUsername(username);
    if (user == null || !password.equals(user.getPassword())) {
      throw new AuthenticationException("Invalid login and/or password.");
    }

    return jwtService.generateJwtToken(Principal.fromUser(user));
  }
}
