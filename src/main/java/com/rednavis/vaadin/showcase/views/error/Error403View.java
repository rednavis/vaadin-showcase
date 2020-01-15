package com.rednavis.vaadin.showcase.views.error;

import static com.rednavis.vaadin.showcase.views.ConstantUtils.PAGE_ERROR403_TITLE;

import com.rednavis.vaadin.showcase.components.ErrorHttpComponent;
import com.rednavis.vaadin.showcase.components.ErrorHttpComponent.ErrorHttpEnum;
import com.rednavis.vaadin.showcase.exception.ForbiddenError;
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

@PageTitle(PAGE_ERROR403_TITLE)
@Tag("error-view")
@JsModule("./src/views/error/error-view.js")
public class Error403View extends PolymerTemplate<TemplateModel> implements HasErrorParameter<ForbiddenError> {

  @Id("content")
  private Div content;

  public Error403View() {
    ErrorHttpComponent errorHttpComponent = new ErrorHttpComponent(ErrorHttpEnum.ERROR403, "rednavis@rednavis.com");
    content.add(errorHttpComponent);
  }

  @Override
  public int setErrorParameter(BeforeEnterEvent event, ErrorParameter<ForbiddenError> parameter) {
    return HttpServletResponse.SC_FORBIDDEN;
  }

}
