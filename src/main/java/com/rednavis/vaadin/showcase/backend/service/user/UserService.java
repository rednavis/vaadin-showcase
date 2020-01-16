package com.rednavis.vaadin.showcase.backend.service.user;

import com.rednavis.vaadin.showcase.backend.dto.UserDto;
import com.rednavis.vaadin.showcase.backend.entity.UserEntity;

/**
 * UserService.
 */
public interface UserService {

  /**
   * Get user by ID with nested entities
   *
   * @return {@link UserDto}
   */
  UserDto getUserById(long id);

  /**
   * Get user by email.
   *
   * @return {@link UserDto}
   */
  UserDto getUserByEmail(String email);

  /**
   * Get user entity by ID with nested entities
   *
   * @return {@link UserEntity}
   */
  UserEntity getUserEntityById(long id);

  /**
   * Get user entity by Email with nested entities
   *
   * @return {@link UserEntity}
   */
  UserEntity getUserEntityByEmail(String email);
  
  /**
   * Update user in DB.
   *
   * @return count of update users
   */
  int updateUser(UserDto userDto);

  /**
   * Create user and his roles in DB with provided customerId.
   *
   * @return new user ID
   */
  long createUser(UserDto userDto);

  /**
   * Delete user and his roles from DB.
   *
   * @return count of delete users
   */
  int deleteUser(long id);
}
