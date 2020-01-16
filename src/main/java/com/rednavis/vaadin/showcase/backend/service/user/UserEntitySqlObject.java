package com.rednavis.vaadin.showcase.backend.service.user;

import static com.rednavis.vaadin.showcase.backend.db.DbConstants.ROLE_PREFIX;

import com.rednavis.vaadin.showcase.backend.db.DbConstants;
import com.rednavis.vaadin.showcase.backend.entity.RoleEntity;
import com.rednavis.vaadin.showcase.backend.entity.UserEntity;
import com.rednavis.vaadin.showcase.backend.service.role.RoleRepository;
import org.jdbi.v3.sqlobject.CreateSqlObject;
import org.jdbi.v3.sqlobject.config.RegisterFieldMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.customizer.Define;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.statement.UseRowReducer;
import org.jdbi.v3.sqlobject.transaction.Transaction;
import org.jdbi.v3.stringtemplate4.UseStringTemplateSqlLocator;

/**
 * Jdbi Sql object for working with users table in DB
 */
public interface UserEntitySqlObject {

  @CreateSqlObject
  RoleRepository roleRepository();

  /**
   * Get user entity by id with nested entities. Prefixes params are used to construct correct sql query, that mapper then can map nested entities,
   * {@link DbConstants} for values
   *
   * @param rolePrefix role prefix
   * @param id of user to be returned
   * @return {@link UserEntity} object
   */
  @SqlQuery
  @UseStringTemplateSqlLocator
  @RegisterFieldMapper(UserEntity.class)
  @RegisterFieldMapper(value = RoleEntity.class, prefix = ROLE_PREFIX)
  @UseRowReducer(UserReducer.class)
  UserEntity getUserById(
      @Define("role_prefix") String rolePrefix,
      @Bind("id") long id);

  /**
   * Get user by email.
   *
   * @return {@link UserEntity} object
   */
  @SqlQuery
  @UseStringTemplateSqlLocator
  @RegisterFieldMapper(UserEntity.class)
  @RegisterFieldMapper(value = RoleEntity.class, prefix = ROLE_PREFIX)
  @UseRowReducer(UserReducer.class)
  UserEntity getUserByEmail(
      @Define("role_prefix") String rolePrefix,
      @Bind("email") String email);

  /**
   * Update user in DB without update of password for security purpose
   *
   * @return count of update users
   */
  @SqlUpdate("update users set email = :email where id = :id")
  int updateUserWithoutPassword(@BindBean UserEntity userEntity);

  @Transaction
  default int updateFullUserWithoutPassword(UserEntity userEntity) {
    long userId = userEntity.getId();
    roleRepository().deleteUserRoles(userId);

    if (userEntity.getRoleSet() != null && userEntity.getRoleSet().size() > 0) {
      roleRepository().insertUserRoles(userId, userEntity.getRoleSet());
    }

    return updateUserWithoutPassword(userEntity);
  }

  /**
   * Insert user in DB.
   *
   * @return new user ID
   */
  @SqlUpdate("insert into users (email, password) values (:email, :password)")
  @GetGeneratedKeys
  long insertUser(@BindBean UserEntity userEntity);

  /**
   * Insert user with existed customer and his roles in DB.
   *
   * @return new user ID
   */
  @Transaction
  default long insertFullUser(UserEntity user) {
    long id = insertUser(user);

    if (user.getRoleSet() != null && user.getRoleSet().size() > 0) {
      roleRepository().insertUserRoles(id, user.getRoleSet());
    }

    return id;
  }

  /**
   * Delete user from DB.
   *
   * @return count of delete users
   */
  @SqlUpdate("delete from users where id = :id")
  int deleteUser(@Bind("id") long id);

  @Transaction
  default int deleteFullUser(long id) {
    roleRepository().deleteUserRoles(id);
    return deleteUser(id);
  }
}