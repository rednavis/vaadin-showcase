package com.rednavis.vaadin.showcase.backend.auth.jwt;

import com.rednavis.vaadin.showcase.backend.auth.annotations.Secured;
import com.rednavis.vaadin.showcase.backend.auth.exception.AccessDeniedException;
import java.io.IOException;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Priority;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ResourceInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * JWT Authentication filter is responsible for checking JWT token in request's header.
 */
@WebFilter(
    urlPatterns = { "/*" },
    //use symbol ';' for splitting excluded urls
    initParams = { @WebInitParam(name = "excludedUrls", value = "/VAADIN/") }
)
@Priority(Priorities.AUTHORIZATION)
@Slf4j
@RequestScoped
@Secured
public class JwtAuthorisationFilter implements Filter {

  private static Set<String> excluded = Collections.emptySet();

  @Inject
  private SecurityContext securityContext;
  @Inject
  private ResourceInfo resourceInfo;

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    String excludedString = filterConfig.getInitParameter("excludedUrls");
    if (excludedString != null) {
      excluded = Collections.unmodifiableSet(
          new HashSet<>(Arrays.asList(excludedString.split(";", 0))));
    }
  }

  @Override
  public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) req;
    HttpServletResponse response = (HttpServletResponse) resp;

    //check request url on excluded url
    if (isExcluded(request)) {
      filterChain.doFilter(request, response);
      return;
    }

    // Get the resource class which matches with the requested URL
    // Extract the roles declared by it
    Class<?> resourceClass = resourceInfo.getResourceClass();
    List<Role> classRoles = extractRoles(resourceClass);

    // Get the resource method which matches with the requested URL
    // Extract the roles declared by it
    Method resourceMethod = resourceInfo.getResourceMethod();
    List<Role> methodRoles = extractRoles(resourceMethod);

    try {

      // Check if the user is allowed to execute the method
      // The method annotations override the class annotations
      if (methodRoles.isEmpty()) {
        checkPermissions(classRoles);
      } else {
        checkPermissions(methodRoles);
      }

    } catch (Exception e) {
      response.setStatus(HttpServletResponse.SC_FORBIDDEN);
      response.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
    }

  }

  @Override
  public void destroy() {

  }

  private boolean isExcluded(HttpServletRequest request) {
    String path = request.getRequestURI();
    return excluded.stream().anyMatch(path::startsWith);
  }

  /**
   * Extract the roles from the annotated element.
   * @param annotatedElement annotated element
   * @return List<Role>
   */
  private List<Role> extractRoles(AnnotatedElement annotatedElement) {
    if (annotatedElement == null) {
      return new ArrayList<>();
    } else {
      Secured secured = annotatedElement.getAnnotation(Secured.class);
      if (secured == null) {
        return new ArrayList<>();
      } else {
        Role[] allowedRoles = secured.value();
        return Arrays.asList(allowedRoles);
      }
    }
  }

  private void checkPermissions(List<Role> allowedRoles) throws Exception {
    List<Role> userRoles = securityContext.getRoles();
    if (userRoles == null || userRoles.isEmpty()) {
      return;
    }
    if (allowedRoles.stream().noneMatch(userRoles::contains)) {
      throw new AccessDeniedException("You don't have permission to access!");
    }
  }
}
