package com.rednavis.vaadin.showcase.backend.auth.jwt;

import java.util.List;
import java.util.stream.Collectors;
import javax.enterprise.context.RequestScoped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Payload part of JWT token that have user and role information (without password value).
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@RequestScoped
public class JwtPayload {

  private String username;
  private List<String> roles;

  public static JwtPayload fromPrincipal(Principal principal) {
    return JwtPayload.builder()
        .username(principal.getUserName())
        .roles(principal.getRoles().stream().map(Enum::toString).collect(Collectors.toList()))
        .build();
  }

  public Principal toPrincipal() {
    return Principal.builder()
        .userName(username)
        .roles(roles.stream().map(Role::valueOf).collect(Collectors.toList()))
        .build();
  }

}
