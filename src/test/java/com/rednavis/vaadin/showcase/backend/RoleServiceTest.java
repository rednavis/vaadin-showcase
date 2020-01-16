package com.rednavis.vaadin.showcase.backend;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.rednavis.vaadin.showcase.backend.dto.RoleDto;
import com.rednavis.vaadin.showcase.backend.service.role.RoleServiceImpl;
import com.rednavis.vaadin.showcase.extension.testcontainer.PostgreSqlExtension;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {

  private static final long ADMIN_ID = 1L;
  private static final long USER_ID = 2L;
  
  @InjectMocks
  private RoleServiceImpl roleService;

  @RegisterExtension
  public static PostgreSqlExtension dbExtension = new PostgreSqlExtension();
  
  @Test
  public void shouldTestGetUserRoles() {
    Set<RoleDto> adminRoles = roleService.getUserRoles(ADMIN_ID);
    assertEquals(1, adminRoles.size());

    Set<RoleDto> userRoles = roleService.getUserRoles(USER_ID);
    assertEquals(1, userRoles.size());
  }
}
