package com.rednavis.vaadin.showcase.views.dashboard;

import javax.inject.Inject;
import com.rednavis.vaadin.showcase.security.SecurityService;
import com.rednavis.vaadin.showcase.security.utils.SecurityUtils;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

/**
 * Login page representation.
 */
@Tag("sa-login-view")
@Route
@PageTitle("Login")
public class LoginView_ extends VerticalLayout implements AfterNavigationObserver, BeforeEnterObserver {

  @Inject
  private SecurityService securityService;
  private LoginOverlay login = new LoginOverlay();

  public LoginView_(){
    login.setAction("login");
    login.setTitle("Vaadin-showcase");
    login.setDescription(null);
//    login.addLoginListener(e -> {
//      login.close();
//        }
//          boolean isAuthenticated = securityService.authenticate(e);
//          if (isAuthenticated) {
//            UI.getCurrent().navigate(DashboardView.class);
//          } else {
//            login.setError(true);
//          }
//        }
//    );
    getElement().appendChild(login.getElement());
  }

  @Override
  public void beforeEnter(BeforeEnterEvent event) {
    if (SecurityUtils.isUserLoggedIn()) {
//      navigateToMainPage();
      event.forwardTo(DashboardView.class);
      //event.rerouteTo(DashboardView.class);
    } else {
      login.setOpened(true);
    }
  }

  public void afterNavigation(AfterNavigationEvent event) {
    login.setError(
        event.getLocation().getQueryParameters().getParameters().containsKey(
            "error"));
  }

  private void navigateToMainPage() {
    UI.getCurrent().navigate(DashboardView.class);
  }
}
