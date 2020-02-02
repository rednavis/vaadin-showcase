package com.rednavis.vaadin.showcase.jwt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import com.rednavis.vaadin.showcase.backend.auth.exception.JwtTokenException;
import com.rednavis.vaadin.showcase.backend.auth.jwt.JwtService;
import com.rednavis.vaadin.showcase.backend.auth.jwt.Principal;
import com.rednavis.vaadin.showcase.backend.auth.jwt.Role;
import io.jsonwebtoken.ExpiredJwtException;
import java.util.Collections;
import java.util.List;
import org.junit.Test;

/**
 * To test generation/parsing Jwt token.
 */
public class JwtServiceTest {

  private final String username = "user@rednavis.com";
  private final List<Role> roles = Collections.singletonList(Role.USER);
  //generated jwt token with other secret key
  private final String failed_jwt_token = "eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE1OSAyOTkwNjcsInN1YiI6IntcInVzZXJuYW1lXCI6XCJ1c2VyQHJlZG5hdmlzLmNvbVwiLFwicm9sKLNcIjpbXCJVU0VSXCJdfSJ9.4WIAh7YuAzm00sLV2ngXlKOBz8PAQBMln3pwpP2K3XpYuuA_2oAoA_fPGfgr1hhUDyvH7LsTw9FGjCW-u_Tqiw";
  //generated jwt token with expired date
  private final String expiredToken = "eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE1ODAzMDIyMjQsInN1YiI6IntcInVzZXJuYW1lXCI6XCJ1c2VyQHJlZG5hdmlzLmNvbVwiLFwicm9sZXNcIjpbXCJVU0VSXCJdfSJ9.-lqphDpWY85HMYum3MwFA-j-Q-nNLNjXVm01UMoAWsCZe1m21lOUYAMJg7Mi5_0HY17ZPwEHrgnSfBiy3N6V1w";

  private final JwtService jwtService = new JwtService();

  @Test
  public void shouldGenerateAndParseJwtToken() {
    Principal expectedPrincipal = new Principal().toBuilder()
        .userName(username)
        .roles(roles)
        .build();
    //generate jwt token
    String jwtToken = jwtService.generateJwtToken(expectedPrincipal);

    //parse jwt token
    Principal actualPrincipal = jwtService.parseToken(jwtToken);

    assertThat(actualPrincipal.getUserName()).isNotNull();
    assertThat(actualPrincipal.getUserName()).isEqualTo(expectedPrincipal.getUserName());
    assertThat(actualPrincipal.getRoles()).isNotEmpty();
    assertThat(actualPrincipal.getRoles().size()).isEqualTo(expectedPrincipal.getRoles().size());
    assertThat(actualPrincipal.getRoles()).containsExactlyInAnyOrder(roles.toArray(new Role[0]));
  }

  @Test
  public void shouldParseFailedJwtTokenWithException() {

    //parse jwt token
    assertThatExceptionOfType(JwtTokenException.class).isThrownBy(() -> jwtService.parseToken(failed_jwt_token))
        .withMessage("JWT token is invalid");
  }

  @Test
  public void shouldParseJwtTokenWithTokenExpiredException() {
    //parse jwt token
    assertThatExceptionOfType(JwtTokenException.class).isThrownBy(() -> jwtService.parseToken(expiredToken))
        .withRootCauseInstanceOf(ExpiredJwtException.class)
        .withMessage("JWT token expired");
  }
}
