package com.rednavis.vaadin.showcase.backend.auth.jwt;

import static com.rednavis.vaadin.showcase.views.ConstantUtils.AUTHENTICATION_SCHEME;
import static com.rednavis.vaadin.showcase.views.ConstantUtils.PAGE_DEFAULT_URL;
import static com.rednavis.vaadin.showcase.views.ConstantUtils.PAGE_LOGIN_URL;
import static com.rednavis.vaadin.showcase.views.ConstantUtils.ROOT_URL;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rednavis.vaadin.showcase.backend.auth.exception.AuthenticationException;
import com.rednavis.vaadin.showcase.backend.auth.user.MockUserDetailsService;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
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
import javax.ws.rs.core.HttpHeaders;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * JWT Authentication filter is responsible for checking JWT token in request's header.
 */
@WebFilter(
    urlPatterns = { "/*" },
    //use symbol ';' for splitting excluded urls
    initParams = { @WebInitParam(name = "excludedUrls",
        value = "/VAADIN/;/robots.txt;/favicon.ico;/manifest.webmanifes;/sw.js;/offline-page.html;/icons/;/img/;/frontend/;/webjars/;/h2-console/;/frontend-es5/;/frontend-es6/") }
)
@Slf4j
@RequestScoped
public class JwtAuthenticationFilter implements Filter {

  private static Set<String> excluded = Collections.emptySet();
  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  @Inject
  private JwtService jwtService;
  @Inject
  private MockUserDetailsService userService;
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

    String loginURI = ROOT_URL + PAGE_LOGIN_URL;
    String requestUri = request.getRequestURI();

    log.info("Handle request to " + requestUri  + " in servlet's filter...");

    // Get the Authorization header from the request
    String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    log.info("Request header for " + requestUri  + request.getHeader(HttpHeaders.AUTHORIZATION));
    log.info("Response header for " + requestUri  + response.getHeader(HttpHeaders.AUTHORIZATION));

    boolean isLoginRequest = request.getRequestURI().equals(loginURI) || request.getRequestURI().equals(ROOT_URL);

    try {
    // Validate the Authorization header
    if (isTokenBasedAuthentication(authorizationHeader)) {
      log.info("Get token authentication header...");
      // Extract the token from the Authorization header
      jwtToken = authorizationHeader
          .substring(AUTHENTICATION_SCHEME.length()).trim();
      filterChain.doFilter(request, response);
      return;
    } else {
      //todo: response with auth header is cleared before Vaadin servlet. So, move following block to <ConfigureUIServiceInitListener> and use VaadinSession for storing JWT header.

      UserDetail user = getUserFromRequest(request);
      if (isLoginRequest && Objects.nonNull(user)) {
        jwtToken = userService.authenticate(user.getUsername(), user.getPassword());

        response.addHeader(HttpHeaders.AUTHORIZATION, AUTHENTICATION_SCHEME + " " + jwtToken);

        response.setStatus(HttpServletResponse.SC_OK);
        response.sendRedirect(ROOT_URL + PAGE_DEFAULT_URL);
        return;
      }
    }
    } catch (IOException | AuthenticationException ex) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
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
    String json = IOUtils.toString(request.getReader());
    return json == null || StringUtils.isEmpty(json) ? null : OBJECT_MAPPER.readValue(json, UserDetail.class);
  }

  private boolean isExcluded(HttpServletRequest request) {
    String path = request.getRequestURI();
    return excluded.stream().anyMatch(path::startsWith);
  }

  @Override
  public void destroy() {

  }
}
