package com.rednavis.vaadin.showcase.backend.service.role;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.rednavis.vaadin.showcase.backend.entity.RoleEntity;
import com.rednavis.vaadin.showcase.backend.enums.UserRole;
import com.rednavis.vaadin.showcase.utils.BaseIntegrationTest;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.inject.Inject;
import org.jboss.weld.junit5.auto.AddBeanClasses;
import org.jboss.weld.junit5.auto.EnableAutoWeld;
import org.junit.jupiter.api.Test;

@EnableAutoWeld
@AddBeanClasses({RoleRepositoryImpl.class})
public class RoleRepositoryTest extends BaseIntegrationTest {

  private static final long ADMIN_ID = 1L;

  @Inject
  private RoleRepository roleRepository;
  
  @Test
  public void shouldTestRoleRepositoryMethods() {
    Set<RoleEntity> adminRoles = new HashSet<>(Collections.singleton(RoleEntity.builder()
        .id(2L)
        .role(UserRole.USER)
        .build()));
    roleRepository.insertUserRoles(ADMIN_ID, adminRoles);
    
    assertEquals(2, roleRepository.getUserRoles(ADMIN_ID).size());
    assertEquals(2, roleRepository.deleteUserRoles(ADMIN_ID));
    assertEquals(0, roleRepository.getUserRoles(ADMIN_ID).size());
  }
}
