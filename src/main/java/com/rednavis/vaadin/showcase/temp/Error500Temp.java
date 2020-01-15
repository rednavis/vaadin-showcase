package com.rednavis.vaadin.showcase.temp;

import static com.rednavis.vaadin.showcase.views.ConstantUtils.VIEW_PORT;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

@Theme(Lumo.class)
@PageTitle("Error401Temp")
@Route("error500View")
@Tag("error-500-view")
@Viewport(VIEW_PORT)
public class Error500Temp extends Div {

  public Error500Temp() {
    int x = 5 / 0;
  }
}
