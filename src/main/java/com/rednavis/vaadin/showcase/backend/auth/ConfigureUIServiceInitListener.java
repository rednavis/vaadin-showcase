package com.rednavis.vaadin.showcase.backend.auth;

import com.rednavis.vaadin.showcase.backend.auth.annotations.Secured;
import com.rednavis.vaadin.showcase.backend.auth.jwt.SecurityContext;
import com.rednavis.vaadin.showcase.exception.ForbiddenError;
import com.rednavis.vaadin.showcase.views.login.LoginView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import java.lang.annotation.Annotation;
import java.util.stream.Stream;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Listener for Vaadin Services initialization events.
 * It is responsible for checking permissions.
 */
@RequestScoped
@NoArgsConstructor
@Slf4j
public class ConfigureUIServiceInitListener implements VaadinServiceInitListener {

  //todo: after checking in debug mode it is unnecessary to use security context with @RequestScoped for storing logged user (Vaadin use several requests for showing page).
  //todo: So, create hash for storing username, roles and jwt expires date and check user's role in it.
  @Inject
  SecurityContext securityContext;

  @Override
  public void serviceInit(ServiceInitEvent serviceInitEvent) {
    serviceInitEvent.getSource().addUIInitListener(uiEvent -> {
      final UI ui = uiEvent.getUI();
      ui.addBeforeEnterListener(this::beforeEnter);
    });
  }

  /**
   * Reroutes the user if (s)he is not authorized to access the view.
   *
   * @param event before navigation event with event details
   */
  private void beforeEnter(BeforeEnterEvent event) {
    Class routeClass = event.getNavigationTarget();

      if (!isAccessGranted(routeClass)) {
        if (isLoggedIn()) {
          event.rerouteToError(ForbiddenError.class);
        } else {
          event.rerouteTo(LoginView.class);
        }
      }
  }

  private boolean isLoggedIn() {
    return securityContext.getUserName() != null;
  }

  private boolean isAccessGranted(Class routeClass) {
    Annotation securedAnnotation = routeClass.getAnnotation(Secured.class);
    if (securedAnnotation != null && ((Secured) securedAnnotation).value().length != 0 &&
        (securityContext.getRoles() != null && !securityContext.getRoles().isEmpty())) {
      return Stream.of(((Secured) securedAnnotation).value()).anyMatch(role -> securityContext.getRoles().contains(role));
    } else
      return true;
  }
}
