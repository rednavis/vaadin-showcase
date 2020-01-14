package com.rednavis.vaadin.showcase.views.error;

import static com.rednavis.vaadin.showcase.views.ConstantUtils.PAGE_ERROR404_TITLE;

import com.rednavis.vaadin.showcase.components.ErrorHttpComponent;
import com.rednavis.vaadin.showcase.components.ErrorHttpComponent.ErrorHttpEnum;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.ErrorParameter;
import com.vaadin.flow.router.HasErrorParameter;
import com.vaadin.flow.router.NotFoundException;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.templatemodel.TemplateModel;
import javax.servlet.http.HttpServletResponse;

@PageTitle(PAGE_ERROR404_TITLE)
@Tag("error-view")
@JsModule("./src/views/error/error-view.js")
public class Error404View extends PolymerTemplate<TemplateModel> implements HasErrorParameter<NotFoundException> {

  @Id("content")
  private Div content;

  public Error404View() {
    ErrorHttpComponent errorHttpComponent = new ErrorHttpComponent(ErrorHttpEnum.ERROR404, "rednavis@rednavis.com");
    content.add(errorHttpComponent);
  }

  @Override
  public int setErrorParameter(BeforeEnterEvent event, ErrorParameter<NotFoundException> parameter) {
    return HttpServletResponse.SC_NOT_FOUND;
  }

}
