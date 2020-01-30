package com.rednavis.vaadin.showcase.backend.service.role;

import com.rednavis.vaadin.showcase.backend.entity.RoleEntity;
import java.util.Set;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlBatch;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.transaction.Transaction;

/**
 * RoleRepository
 */
public interface RoleRepository {

  @SqlBatch("insert into user_role (user_id, role_id) values (:userId, :role.id)")
  @Transaction
  void insertUserRoles(@Bind("userId") long userId, @BindBean("role") Set<RoleEntity> roleSet);
  
  @SqlQuery("select roles.id, roles.role from roles join user_role on roles.id = user_role.role_id where user_role.user_id = :userId")
  @RegisterBeanMapper(RoleEntity.class)
  Set<RoleEntity> getUserRoles(@Bind("userId") long userId);

  @SqlUpdate("delete from user_role where user_id = :userId")
  int deleteUserRoles(@Bind("userId") long userId);
}
