package com.rednavis.vaadin.showcase.backend;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.rednavis.vaadin.showcase.backend.dto.RoleDto;
import com.rednavis.vaadin.showcase.backend.service.role.RoleService;
import com.rednavis.vaadin.showcase.backend.service.role.RoleServiceImpl;
import com.rednavis.vaadin.showcase.rule.testcontainer.PostgreSqlDbRule;
import java.util.Set;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class RoleServiceTest {

  private static final long ADMIN_ID = 1L;
  private static final long USER_ID = 2L;
  
  @Rule
  public PostgreSqlDbRule dbRule = new PostgreSqlDbRule();
  
  @Test
  public void shouldTestGetUserRoles() {
    Set<RoleDto> adminRoles = RoleServiceImpl.instance().getUserRoles(ADMIN_ID);
    assertEquals(1, adminRoles.size());

    Set<RoleDto> userRoles = RoleServiceImpl.instance().getUserRoles(USER_ID);
    assertEquals(1, userRoles.size());
  }
}
