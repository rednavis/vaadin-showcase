package com.rednavis.vaadin.showcase.views.login;

import static com.rednavis.vaadin.showcase.views.ConstantUtils.PAGE_LOGIN_TITLE;
import static com.rednavis.vaadin.showcase.views.ConstantUtils.PAGE_LOGIN_URL;
import static com.rednavis.vaadin.showcase.views.ConstantUtils.VIEW_PORT;

import com.rednavis.vaadin.showcase.temp.Error401Temp;
import com.rednavis.vaadin.showcase.temp.Error403Temp;
import com.rednavis.vaadin.showcase.temp.Error500Temp;
import com.rednavis.vaadin.showcase.views.ConstantUtils;
import com.rednavis.vaadin.showcase.views.dashboard.DashboardView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.dom.Element;
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
@Viewport(VIEW_PORT)
public class LoginView extends PolymerTemplate<TemplateModel> {

  @Id("content")
  private Div content;

  private TextField userName;
  private PasswordField password;
  private Label userNameLabel;
  private Label passwordLabel;
  private Checkbox rememberMe;
  private Button loginButton;

  /**
   * LoginView.
   */
  public LoginView() {
    createForm();
    Button goDashboard = new Button("Go Dashboard");
    goDashboard.addClickListener(
        (ComponentEventListener<ClickEvent<Button>>) buttonClickEvent -> UI.getCurrent().navigate(DashboardView.class));

    Button error401 = new Button("Error 401");
    error401.addClickListener((ComponentEventListener<ClickEvent<Button>>) buttonClickEvent -> UI.getCurrent().navigate(Error401Temp.class));

    Button error403 = new Button("Error 403");
    error403.addClickListener((ComponentEventListener<ClickEvent<Button>>) buttonClickEvent -> UI.getCurrent().navigate(Error403Temp.class));

    Button error404 = new Button("Error 404");
    error404.addClickListener((ComponentEventListener<ClickEvent<Button>>) buttonClickEvent -> UI.getCurrent().navigate("wrongUrl"));

    Button error500 = new Button("Error 500");
    error500.addClickListener((ComponentEventListener<ClickEvent<Button>>) buttonClickEvent -> UI.getCurrent().navigate(Error500Temp.class));

    VerticalLayout verticalLayout = new VerticalLayout(goDashboard, error401, error403, error404, error500);
    content.add(verticalLayout);
  }

  private void createForm() {
    userName = new TextField();
    password = new PasswordField();
    loginButton = new Button();
    userNameLabel = new Label();
    passwordLabel = new Label();
    rememberMe = new Checkbox();
    Element userNameInput = new Element(Tag.INPUT);
    Element passwordInput = new Element(Tag.INPUT);
    userNameInput.setAttribute("slot", "input");
    userNameInput.setAttribute("name", "username");
    passwordInput.setAttribute("slot", "input");
    passwordInput.setAttribute("name", "password");
    userName.getElement().appendChild(userNameInput);
    password.getElement().appendChild(passwordInput);
    loginButton.setId("login");
    userNameLabel.setText(ConstantUtils.LOGIN_FORM_LOGIN);
    passwordLabel.setText(ConstantUtils.LOGIN_FORM_PASSWORD);
    userName.setPlaceholder(ConstantUtils.LOGIN_FORM_TYPE_A_LOGIN);
    password.setPlaceholder(ConstantUtils.LOGIN_FORM_TYPE_A_PASSWORD);
    rememberMe.setLabel(ConstantUtils.LOGIN_FORM_REMEMBER_ME);
    loginButton.setText(ConstantUtils.BUTTON_LOGIN);
  }
}
