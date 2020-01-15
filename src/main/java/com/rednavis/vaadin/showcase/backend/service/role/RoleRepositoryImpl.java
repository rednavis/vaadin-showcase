package com.rednavis.vaadin.showcase.backend.service.role;

import com.rednavis.vaadin.showcase.backend.db.Dbi;
import com.rednavis.vaadin.showcase.backend.entity.RoleEntity;
import java.util.Set;
import org.jdbi.v3.core.Jdbi;

/**
 * RoleRepositoryImpl.
 */
public class RoleRepositoryImpl implements RoleRepository {

  private static final Object LOCK = new Object();

  private static RoleRepositoryImpl INSTANCE;
  private Jdbi _jdbi;

  private RoleRepositoryImpl() {
    _jdbi = Dbi.instance().getJdbi();
  }

  public static RoleRepositoryImpl instance() {
    if (INSTANCE != null) {
      return INSTANCE;
    }
    synchronized (LOCK) {
      if (INSTANCE == null) {
        INSTANCE = new RoleRepositoryImpl();
      }
      return INSTANCE;
    }
  }
  
  @Override
  public Set<RoleEntity> getUserRoles(long userId) {
    return _jdbi.withExtension(RoleRepository.class, sql -> sql.getUserRoles(userId));
  }
}
