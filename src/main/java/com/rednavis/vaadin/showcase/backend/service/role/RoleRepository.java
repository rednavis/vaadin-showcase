package com.rednavis.vaadin.showcase.backend.service.role;

import com.rednavis.vaadin.showcase.backend.entity.RoleEntity;
import java.util.Set;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

/**
 * RoleRepository
 */
public interface RoleRepository {

  @SqlQuery("select roles.id, roles.role from roles join user_role on roles.id = user_role.role_id where user_role.user_id = :userId")
  @RegisterBeanMapper(RoleEntity.class)
  Set<RoleEntity> getUserRoles(@Bind("userId") long userId);
}
