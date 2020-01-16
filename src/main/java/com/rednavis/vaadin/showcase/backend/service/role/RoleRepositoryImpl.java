package com.rednavis.vaadin.showcase.backend.service.role;

import com.rednavis.vaadin.showcase.backend.db.Dbi;
import com.rednavis.vaadin.showcase.backend.entity.RoleEntity;
import java.util.Set;
import javax.inject.Singleton;
import org.jdbi.v3.core.Jdbi;

/**
 * RoleRepositoryImpl.
 */
@Singleton
public class RoleRepositoryImpl implements RoleRepository {
  
  private final Jdbi jdbi = Dbi.instance().getJdbi();

  @Override
  public void insertUserRoles(long userId, Set<RoleEntity> roleSet) {
    jdbi.useExtension(RoleRepository.class, sql -> sql.insertUserRoles(userId, roleSet));
  }
  
  @Override
  public Set<RoleEntity> getUserRoles(long userId) {
    return jdbi.withExtension(RoleRepository.class, sql -> sql.getUserRoles(userId));
  }

  @Override
  public int deleteUserRoles(long userId) {
    return jdbi.withExtension(RoleRepository.class, sql -> sql.deleteUserRoles(userId));
  }
}
