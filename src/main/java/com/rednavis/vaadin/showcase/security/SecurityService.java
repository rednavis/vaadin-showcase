package com.rednavis.vaadin.showcase.security;

import javax.enterprise.context.ApplicationScoped;
import com.vaadin.flow.component.login.AbstractLogin;

/**
 * Service is responsible for managing of authentication.
 */
@ApplicationScoped
public class SecurityService {

  //todo
  public boolean authenticate(AbstractLogin.LoginEvent e) {
    return true;
  }

}
