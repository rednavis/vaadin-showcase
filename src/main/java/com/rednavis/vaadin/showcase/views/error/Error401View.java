package com.rednavis.vaadin.showcase.views.error;

import static com.rednavis.vaadin.showcase.views.ConstantUtils.PAGE_ERROR401_TITLE;

import com.rednavis.vaadin.showcase.components.ErrorHttpComponent;
import com.rednavis.vaadin.showcase.components.ErrorHttpComponent.ErrorHttpEnum;
import com.rednavis.vaadin.showcase.exception.UnauthorizedError;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.ErrorParameter;
import com.vaadin.flow.router.HasErrorParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.templatemodel.TemplateModel;
import javax.servlet.http.HttpServletResponse;

@PageTitle(PAGE_ERROR401_TITLE)
@Tag("error-view")
@JsModule("./src/views/error/error-view.js")
public class Error401View extends PolymerTemplate<TemplateModel> implements HasErrorParameter<UnauthorizedError> {

  @Id("content")
  private Div content;

  public Error401View() {
    ErrorHttpComponent errorHttpComponent = new ErrorHttpComponent(ErrorHttpEnum.ERROR401, "rednavis@rednavis.com");
    content.add(errorHttpComponent);
  }

  @Override
  public int setErrorParameter(BeforeEnterEvent event, ErrorParameter<UnauthorizedError> parameter) {
    return HttpServletResponse.SC_UNAUTHORIZED;
  }

}
