package com.rednavis.vaadin.showcase.views.login;

import static com.rednavis.vaadin.showcase.views.ConstantUtils.PAGE_LOGIN_TITLE;
import static com.rednavis.vaadin.showcase.views.ConstantUtils.PAGE_LOGIN_URL;
import static com.rednavis.vaadin.showcase.views.ConstantUtils.VIEW_PORT;

import com.rednavis.vaadin.showcase.views.dashboard.DashboardView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.templatemodel.TemplateModel;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

@Theme(Lumo.class)
@PageTitle(PAGE_LOGIN_TITLE)
@Route(PAGE_LOGIN_URL)
@RouteAlias(value = "")
@Tag("login-view")
@JsModule("./src/views/login/login-view.js")
@Viewport(VIEW_PORT)
public class LoginView extends PolymerTemplate<TemplateModel> {

  @Id("content")
  private Div content;

  /**
   * LoginView.
   */
  public LoginView() {
    Button goDashboard = new Button("Go Dashboard");
    goDashboard.addClickListener(
        (ComponentEventListener<ClickEvent<Button>>) buttonClickEvent -> UI.getCurrent().navigate(DashboardView.class));

    content.add(goDashboard);
  }
}