package com.rednavis.vaadin.showcase.temp;

import static com.rednavis.vaadin.showcase.views.ConstantUtils.VIEW_PORT;

import com.rednavis.vaadin.showcase.exception.UnauthorizedError;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

@Theme(Lumo.class)
@PageTitle("Error401Temp")
@Route("error401View")
@Tag("error-401-view")
@Viewport(VIEW_PORT)
public class Error401Temp extends Div implements BeforeEnterObserver {

  @Override
  public void beforeEnter(BeforeEnterEvent event) {
    event.rerouteToError(UnauthorizedError.class);
  }
}
