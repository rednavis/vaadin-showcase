package com.rednavis.vaadin.showcase.backend.auth.jwt;

import com.rednavis.vaadin.showcase.backend.auth.exception.AuthenticationException;
import com.rednavis.vaadin.showcase.backend.auth.user.MockUserDetailsService;
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
  @Inject
  private MockUserDetailsService userService;
  @Inject
  private SecurityContext securityContext;
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

    Principal principal = null;
    // Validate the Authorization header
    if (isTokenBasedAuthentication(authorizationHeader)) {
      // Extract the token from the Authorization header
      jwtToken = authorizationHeader
          .substring(AUTHENTICATION_SCHEME.length()).trim();
      principal = jwtService.parseToken(jwtToken);
    } else
      if (request.getRequestURI().equals(loginURI) &&
      request.getParameter("username") != null && request.getParameter("password") != null) {
        try {
          jwtToken = userService.authenticate(request.getParameter("username"), request.getParameter("password"));
          response.setHeader(HttpHeaders.AUTHORIZATION, AUTHENTICATION_SCHEME + " " + jwtToken);
          //todo: add function for getting principal with token after authentication
          principal = jwtService.parseToken(jwtToken);
        } catch (AuthenticationException ex) {
          response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
          response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
          return;
        }
      }
    if (principal != null && principal.getUserName() != null) {
      securityContext.setUserName(principal.getUserName());
      securityContext.setRoles(principal.getRoles());
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
