package com.rednavis.vaadin.showcase.backend.auth.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rednavis.vaadin.showcase.backend.auth.exception.JwtTokenException;
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
import lombok.extern.slf4j.Slf4j;

/**
 * JWT Service is used for working with Jwt token.
 */
@ApplicationScoped
@Slf4j
public class JwtService {

  //todo: move to properties file
  private static final String SECURITY_KEY = "52A1B17AE460FE257E6A91A58E43B5129D61E1647BCD1C72C8B66D83996B7899";
  private static final long DURATION = 1800;

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();


  public Principal parseToken(String token) {
    log.info("Parsing Jwt token...");
    try {
      Jws<Claims> claims = Jwts.parser()
          .setSigningKey(Keys.hmacShaKeyFor(SECURITY_KEY.getBytes()))
          .parseClaimsJws(token);
      JwtPayload jwtPayload = OBJECT_MAPPER.readValue(claims.getBody().getSubject(), JwtPayload.class);
      return jwtPayload.toPrincipal();
    } catch (ExpiredJwtException ex) {
      throw new JwtTokenException("JWT token expired", ex);
    } catch (Exception ex) {
      throw new JwtTokenException("JWT token is invalid", ex);
    }
  }

  public String generateJwtToken(Principal principal) {
    JwtPayload jwtPayload = JwtPayload.fromPrincipal(principal);
    String subject;
    try {
      subject = OBJECT_MAPPER.writeValueAsString(jwtPayload);
    } catch (JsonProcessingException ex) {
      throw new JwtTokenException("Jwt payload's serialisation is failed", ex);
    }
    log.info("Generating Jwt token...");
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
