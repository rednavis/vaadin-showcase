package com.rednavis.vaadin.showcase.views.dashboard;

import javax.inject.Inject;
import com.rednavis.vaadin.showcase.security.SecurityService;
import com.rednavis.vaadin.showcase.security.utils.SecurityUtils;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

/**
 * Login page representation.
 */
@Tag("sa-login-view")
@Route(value = "login")
@PageTitle("Login")
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

  @Inject
  private SecurityService securityService;
  private LoginOverlay login = new LoginOverlay();

  public LoginView(){
    login.setAction("login");
    login.setOpened(true);
    login.setTitle("Vaadin-showcase");
    login.setDescription(null);
    login.addLoginListener(e -> {
          boolean isAuthenticated = securityService.authenticate(e);
          if (isAuthenticated) {
            navigateToMainPage();
          } else {
            login.setError(true);
          }
        }
    );
    getElement().appendChild(login.getElement());
  }

  @Override
  public void beforeEnter(BeforeEnterEvent event) {
    if (SecurityUtils.isUserLoggedIn()) {
      navigateToMainPage();
      event.rerouteTo(DashboardView.class);
    }
  }

  private void navigateToMainPage() {
    UI.getCurrent().navigate(DashboardView.class);
  }
}
