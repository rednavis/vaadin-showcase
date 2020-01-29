package com.rednavis.vaadin.showcase.backend.service.role;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.rednavis.vaadin.showcase.backend.dto.RoleDto;
import com.rednavis.vaadin.showcase.utils.BaseIntegrationTest;
import java.util.Set;
import javax.inject.Inject;
import org.jboss.weld.junit5.auto.AddBeanClasses;
import org.jboss.weld.junit5.auto.EnableAutoWeld;
import org.junit.jupiter.api.Test;

@EnableAutoWeld
@AddBeanClasses({RoleRepositoryImpl.class, RoleServiceImpl.class})
public class RoleServiceTest extends BaseIntegrationTest {

  private static final long ADMIN_ID = 1L;
  private static final long USER_ID = 2L;

  @Inject
  private RoleService roleService;
  
  @Test
  public void shouldTestGetUserRoles() {
    Set<RoleDto> adminRoles = roleService.getUserRoles(ADMIN_ID);
    assertEquals(1, adminRoles.size());

    Set<RoleDto> userRoles = roleService.getUserRoles(USER_ID);
    assertEquals(1, userRoles.size());
  }
}
