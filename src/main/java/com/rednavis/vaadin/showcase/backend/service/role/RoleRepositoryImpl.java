package com.rednavis.vaadin.showcase.backend.service.role;

import com.rednavis.vaadin.showcase.backend.db.Dbi;
import com.rednavis.vaadin.showcase.backend.entity.RoleEntity;
import java.util.Set;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * RoleRepositoryImpl.
 */
@ApplicationScoped
public class RoleRepositoryImpl implements RoleRepository {

  @Inject
  private Dbi dbi;

  @Override
  public void insertUserRoles(long userId, Set<RoleEntity> roleSet) {
    dbi.getJdbi().useExtension(RoleRepository.class, sql -> sql.insertUserRoles(userId, roleSet));
  }
  
  @Override
  public Set<RoleEntity> getUserRoles(long userId) {
    return dbi.getJdbi().withExtension(RoleRepository.class, sql -> sql.getUserRoles(userId));
  }

  @Override
  public int deleteUserRoles(long userId) {
    return dbi.getJdbi().withExtension(RoleRepository.class, sql -> sql.deleteUserRoles(userId));
  }
}
