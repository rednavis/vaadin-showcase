package com.rednavis.vaadin.showcase.backend.auth.jwt;

import static com.rednavis.vaadin.showcase.views.ConstantUtils.AUTHENTICATION_SCHEME;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rednavis.vaadin.showcase.backend.auth.exception.AuthenticationException;
import com.rednavis.vaadin.showcase.backend.auth.user.MockUserDetailsService;
import com.rednavis.vaadin.showcase.views.ConstantUtils;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
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
import javax.ws.rs.core.HttpHeaders;
import lombok.extern.slf4j.Slf4j;

/**
 * JWT Authentication filter is responsible for checking JWT token in request's header.
 */
@WebFilter(
    urlPatterns = { "/*" },
    //use symbol ';' for splitting excluded urls
    initParams = { @WebInitParam(name = "excludedUrls", value = "/VAADIN/") }
)
@Priority(Priorities.AUTHENTICATION)
@Slf4j
@RequestScoped
public class JwtAuthenticationFilter implements Filter {

  private static Set<String> excluded = Collections.emptySet();
  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  @Inject
  private JwtService jwtService;
  @Inject
  private MockUserDetailsService userService;
  @Inject
  private SecurityContext securityContext;
  private String jwtToken;

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

    String loginURI = request.getContextPath() + ConstantUtils.PAGE_LOGIN_URL;
    String requestUri = request.getRequestURI();

    log.info("Handle request to " + requestUri  + " in servlet's filter...");

    // Get the Authorization header from the request
    String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

    boolean isLoginRequest = request.getRequestURI().equals(loginURI);

    Principal principal = null;
    try {
    // Validate the Authorization header
    if (isTokenBasedAuthentication(authorizationHeader)) {
      log.info("Get token authentication header...");
      // Extract the token from the Authorization header
      jwtToken = authorizationHeader
          .substring(AUTHENTICATION_SCHEME.length()).trim();
      principal = jwtService.parseToken(jwtToken);
    } else {
      long lines = request.getReader().lines().count();
      if (isLoginRequest && lines != 0) {

          UserDetail user = getUserFromRequest(request);
          jwtToken = userService.authenticate(user.getUserName(), user.getPassword());
          response.setHeader(HttpHeaders.AUTHORIZATION, AUTHENTICATION_SCHEME + " " + jwtToken);
          //todo: add function for getting principal with token after authentication
          principal = jwtService.parseToken(jwtToken);
      }
    }
    } catch (IOException | AuthenticationException ex) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
      return;
    }
    if (principal != null) {
      securityContext.setUserName(principal.getUserName());
      securityContext.setRoles(principal.getRoles());
      filterChain.doFilter(request, response);
      return;
    }
      if(isLoginRequest) {
        filterChain.doFilter(request, response);
      } else {
        response.sendRedirect(loginURI);
      }

  }

  private boolean isTokenBasedAuthentication(String authorizationHeader) {

    // Check if the Authorization header is valid
    // It must not be null and must be prefixed with "Bearer" plus a whitespace
    // The authentication scheme comparison must be case-insensitive
    return authorizationHeader != null && authorizationHeader.toLowerCase()
        .startsWith(AUTHENTICATION_SCHEME.toLowerCase() + " ");
  }

  private UserDetail getUserFromRequest(HttpServletRequest request) throws IOException {
    return OBJECT_MAPPER.readValue(request.getReader(), UserDetail.class);
  }

  private boolean isExcluded(HttpServletRequest request) {
    String path = request.getRequestURI();
    return excluded.stream().anyMatch(path::startsWith);
  }

  @Override
  public void destroy() {

  }
}
