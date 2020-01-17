package com.rednavis.vaadin.showcase.backend;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.rednavis.vaadin.showcase.backend.dto.RoleDto;
import com.rednavis.vaadin.showcase.backend.service.role.RoleRepositoryImpl;
import com.rednavis.vaadin.showcase.backend.service.role.RoleService;
import com.rednavis.vaadin.showcase.backend.service.role.RoleServiceImpl;
import com.rednavis.vaadin.showcase.utils.BaseIntegrationTest;
import java.util.Set;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class RoleServiceTest extends BaseIntegrationTest {

  private static final long ADMIN_ID = 1L;
  private static final long USER_ID = 2L;
  
  private static RoleService roleService;
  
  @BeforeAll
  public static void init() {
    roleService = new RoleServiceImpl(new RoleRepositoryImpl());
  }
  
  @Test
  public void shouldTestGetUserRoles() {
    Set<RoleDto> adminRoles = roleService.getUserRoles(ADMIN_ID);
    assertEquals(1, adminRoles.size());

    Set<RoleDto> userRoles = roleService.getUserRoles(USER_ID);
    assertEquals(1, userRoles.size());
  }
}
