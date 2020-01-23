package com.rednavis.vaadin.showcase.backend.auth.jwt;

import java.io.IOException;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;
import lombok.extern.slf4j.Slf4j;

/**
 * JWT Authentication filter is responsible for checking JWT token in request's header.
 */
@WebFilter("/*")
@Slf4j
@RequestScoped
public class JwtAuthFilter implements Filter {

  private static final String AUTHENTICATION_SCHEME = "Bearer";

  @Inject
  private JwtService jwtService;
  private String jwtToken;

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {

  }

  @Override
  public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {

    HttpServletRequest request = (HttpServletRequest) req;
    HttpServletResponse response = (HttpServletResponse) resp;

    String loginURI = request.getContextPath() + "/loginView";

    // Get the Authorization header from the request
    String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    // Validate the Authorization header
    if (isTokenBasedAuthentication(authorizationHeader)) {
      // Extract the token from the Authorization header
      jwtToken = authorizationHeader
          .substring(AUTHENTICATION_SCHEME.length()).trim();
    }

    Principal principal = jwtService.parseToken(jwtToken);

    boolean loggedIn = principal != null && principal.getUserName() != null;

    boolean loginRequest = request.getRequestURI().equals(loginURI);

    if (loggedIn || loginRequest) {
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

  @Override
  public void destroy() {

  }
}
