package com.rednavis.vaadin.showcase.backend.service.user;

import com.rednavis.vaadin.showcase.backend.entity.UserEntity;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;

/**
 * UserRepository.
 */
public interface UserRepository {

  /**
   * Get user by ID with nested entities.
   *
   * @return {@link UserEntity}
   */
  UserEntity getUserById(long id);
  
  /**
   * Get user by email.
   *
   * @return {@link UserEntity}
   */
  UserEntity getUserByEmail(@Bind("email") String email);

  /**
   * Update user in DB without update of password for security purpose
   *
   * @return count of update users
   */
  int updateUserWithoutPassword(@BindBean UserEntity userEntity);
  
  /**
   * Create user with existed customer.
   *
   * @return new user ID
   */
  long insertUser(@BindBean UserEntity userEntity);
  
  /**
   * Delete user and user_role from DB.
   *
   * @return count of delete users
   */
  int deleteUser(long id);
}