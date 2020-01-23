package com.rednavis.vaadin.showcase.backend.auth;

import com.rednavis.vaadin.showcase.views.login.LoginView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import javax.enterprise.context.RequestScoped;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Listener for VaadinService initialization events.
 */
@RequestScoped
@NoArgsConstructor
@Slf4j
public class ConfigureUIServiceInitListener implements VaadinServiceInitListener {

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
      if (!LoginView.class.equals(event.getNavigationTarget())
          && !isLoggedIn()) {
        event.rerouteTo(LoginView.class);
      }
  }

  //todo: move to AuthenticationProvider class and implement
  private boolean isLoggedIn() {
    return true;
  }
}
