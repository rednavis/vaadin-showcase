package com.rednavis.vaadin.showcase.views.login;

import static com.rednavis.vaadin.showcase.views.ConstantUtils.PAGE_LOGIN_TITLE;
import static com.rednavis.vaadin.showcase.views.ConstantUtils.PAGE_LOGIN_URL;
import static com.rednavis.vaadin.showcase.views.ConstantUtils.VIEW_PORT;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.templatemodel.TemplateModel;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

@Theme(value = Lumo.class, variant = Lumo.DARK)
@PageTitle(PAGE_LOGIN_TITLE)
@Route(PAGE_LOGIN_URL)
@RouteAlias(value = "")
@Tag("login-view")
@JsModule("./src/views/login/login-view.js")
//@NpmPackage(value="@polymer/iron-form", version = "3.0.1")
//@NpmPackage(value="@polymer/paper-tabs", version = "3.1.0")
//@NpmPackage(value="@polymer/iron-pages", version = "3.0.1")
@Viewport(VIEW_PORT)
public class LoginView extends PolymerTemplate<TemplateModel> {

//  @Id("content")
//  private Div content;

  /**
   * LoginView.
   */
  public LoginView() {

  }
}
