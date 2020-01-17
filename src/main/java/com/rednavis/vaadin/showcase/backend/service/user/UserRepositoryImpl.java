package com.rednavis.vaadin.showcase.backend.service.user;

import static com.rednavis.vaadin.showcase.backend.db.DbConstantUtils.ROLE_PREFIX;

import com.rednavis.vaadin.showcase.backend.db.Dbi;
import com.rednavis.vaadin.showcase.backend.entity.UserEntity;
import javax.inject.Singleton;
import org.jdbi.v3.core.Jdbi;

/**
 * UserRepositoryImpl.
 */
@Singleton
public final class UserRepositoryImpl implements UserRepository {

  private final Jdbi jdbi = Dbi.instance().getJdbi();

  /**
   * {@inheritDoc}
   */
  @Override
  public UserEntity getUserById(long id) {
    return jdbi.withExtension(UserEntitySqlObject.class, sql -> sql.getUserById(ROLE_PREFIX, id));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UserEntity getUserByEmail(String email) {
    return jdbi.withExtension(UserEntitySqlObject.class, sql -> sql.getUserByEmail(ROLE_PREFIX, email));
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public int updateUserWithoutPassword(UserEntity userEntity) {
    return jdbi.withExtension(UserEntitySqlObject.class, sql -> sql.updateFullUserWithoutPassword(userEntity));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public long insertUser(UserEntity userEntity) {
    return jdbi.withExtension(UserEntitySqlObject.class, sql -> sql.insertFullUser(userEntity));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int deleteUser(long id) {
    return jdbi.withExtension(UserEntitySqlObject.class, sql -> sql.deleteFullUser(id));
  }
}
