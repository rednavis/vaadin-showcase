package com.rednavis.vaadin.showcase.backend.service.role;

import com.rednavis.vaadin.showcase.backend.converter.RoleMapper;
import com.rednavis.vaadin.showcase.backend.dto.RoleDto;
import com.rednavis.vaadin.showcase.backend.entity.RoleEntity;
import java.util.Set;

/**
 * RoleServiceImpl.
 */
public class RoleServiceImpl implements RoleService {

  private static final Object LOCK = new Object();

  private static RoleServiceImpl INSTANCE;
  private RoleRepository _roleRepository;

  private RoleServiceImpl() {
    _roleRepository = RoleRepositoryImpl.instance();
  }

  public static RoleServiceImpl instance() {
    if (INSTANCE != null) {
      return INSTANCE;
    }
    synchronized (LOCK) {
      if (INSTANCE == null) {
        INSTANCE = new RoleServiceImpl();
      }
      return INSTANCE;
    }
  }
  
  @Override
  public Set<RoleDto> getUserRoles(long userId) {
    Set<RoleEntity> roleEntitySet = _roleRepository.getUserRoles(userId);
    return RoleMapper.INSTANCE.setRoleEntityToListRoleDto(roleEntitySet);
  }
}
