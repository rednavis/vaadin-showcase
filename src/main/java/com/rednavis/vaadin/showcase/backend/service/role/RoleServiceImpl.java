package com.rednavis.vaadin.showcase.backend.service.role;

import com.rednavis.vaadin.showcase.backend.converter.RoleMapper;
import com.rednavis.vaadin.showcase.backend.dto.RoleDto;
import com.rednavis.vaadin.showcase.backend.entity.RoleEntity;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * RoleServiceImpl.
 */
@Singleton
public class RoleServiceImpl implements RoleService {
  
  @Inject
  private RoleRepository roleRepository;

  @Override
  public Set<RoleDto> getUserRoles(long userId) {
    Set<RoleEntity> roleEntitySet = roleRepository.getUserRoles(userId);
    return RoleMapper.INSTANCE.entitySetToDtoSet(roleEntitySet);
  }
}
