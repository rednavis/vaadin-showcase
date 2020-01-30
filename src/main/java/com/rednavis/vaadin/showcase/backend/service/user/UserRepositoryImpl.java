package com.rednavis.vaadin.showcase.backend.service.user;

import static com.rednavis.vaadin.showcase.backend.db.DbConstantUtils.ROLE_PREFIX;

import com.rednavis.vaadin.showcase.backend.db.Dbi;
import com.rednavis.vaadin.showcase.backend.entity.UserEntity;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * UserRepositoryImpl.
 */
@ApplicationScoped
public class UserRepositoryImpl implements UserRepository {

  @Inject
  private Dbi dbi;

  /**
   * {@inheritDoc}
   */
  @Override
  public UserEntity getUserById(long id) {
    return dbi.getJdbi().withExtension(UserEntitySqlObject.class, sql -> sql.getUserById(ROLE_PREFIX, id));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UserEntity getUserByEmail(String email) {
    return dbi.getJdbi().withExtension(UserEntitySqlObject.class, sql -> sql.getUserByEmail(ROLE_PREFIX, email));
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public int updateUserWithoutPassword(UserEntity userEntity) {
    return dbi.getJdbi().withExtension(UserEntitySqlObject.class, sql -> sql.updateFullUserWithoutPassword(userEntity));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public long insertUser(UserEntity userEntity) {
    return dbi.getJdbi().withExtension(UserEntitySqlObject.class, sql -> sql.insertFullUser(userEntity));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int deleteUser(long id) {
    return dbi.getJdbi().withExtension(UserEntitySqlObject.class, sql -> sql.deleteFullUser(id));
  }
}
