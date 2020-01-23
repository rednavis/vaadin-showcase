package com.rednavis.vaadin.showcase.backend.auth.jwt;

import com.rednavis.vaadin.showcase.backend.auth.exception.JwtTokenException;
import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import javax.enterprise.context.ApplicationScoped;

/**
 * JWT Service is used for working with Jwt token.
 */
@ApplicationScoped
public class JwtService {

  //todo: move to properties file
  private static final String SECURITY_KEY = "52A1B17AE460FE257E6A91A58E43B5129D61E1647BCD1C72C8B66D83996B7899";
  private static final long DURATION = 1800;

  private static final Gson GSON = new Gson();


  public Principal parseToken(String token) {
    try {
      Jws<Claims> claims = Jwts.parser()
          .setSigningKey(Keys.hmacShaKeyFor(SECURITY_KEY.getBytes()))
          .parseClaimsJws(token);
      JwtPayload jwtPayload = GSON.fromJson(claims.getBody().getSubject(), JwtPayload.class);
      return jwtPayload.toPrincipal();
    } catch (ExpiredJwtException ex) {
      throw new JwtTokenException("JWT token expired", ex);
    } catch (Exception ex) {
      throw new JwtTokenException("JWT token is invalid", ex);
    }
  }

  public String generateJwtToken(Principal principal) {
    JwtPayload jwtPayload = JwtPayload.fromPrincipal(principal);
    String subject = GSON.toJson(jwtPayload);
    return Jwts.builder()
        .setExpiration(expirationDate())
        .setSubject(subject)
        .signWith(Keys.hmacShaKeyFor(SECURITY_KEY.getBytes()))
        .compact();
  }

  private Date expirationDate() {
    Instant expiration = LocalDateTime.now()
        .plusSeconds(DURATION)
        .atZone(ZoneId.systemDefault())
        .toInstant();
    return Date.from(expiration);
  }
}
