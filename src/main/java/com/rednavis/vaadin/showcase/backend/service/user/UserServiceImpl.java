package com.rednavis.vaadin.showcase.backend.service.user;

import com.rednavis.vaadin.showcase.backend.converter.UserMapper;
import com.rednavis.vaadin.showcase.backend.dto.UserDto;
import com.rednavis.vaadin.showcase.backend.entity.UserEntity;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

/**
 * UserServiceImpl.
 */
@Slf4j
@Singleton
public class UserServiceImpl implements UserService {
  
  private UserMapper userMapper = UserMapper.INSTANCE;

  @Inject
  private UserRepository userRepository;

  /**
   * {@inheritDoc}
   */
  @Override
  public UserDto getUserById(long id) {
    UserEntity userEntity = getUserEntityById(id);
    if (userEntity == null) {
      throw new IllegalArgumentException(String.format("User with id = %d not found", id));
    }
    return userMapper.entityToDto(userEntity);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UserDto getUserByEmail(String email) {
    UserEntity userEntity = getUserEntityByEmail(email);
    if (userEntity == null) {
      throw new IllegalArgumentException(String.format("User with id = %s not found", email));
    }
    return userMapper.entityToDto(userEntity);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UserEntity getUserEntityById(long id) {
    return userRepository.getUserById(id);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UserEntity getUserEntityByEmail(String email) {
    return userRepository.getUserByEmail(email);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int updateUser(UserDto userDto) {
    UserEntity userEntity = userMapper.dtoToEntity(userDto);
    return userRepository.updateUserWithoutPassword(userEntity);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public long createUser(UserDto userDto) {
    UserEntity userEntity = userMapper.dtoToEntity(userDto);
    return userRepository.insertUser(userEntity);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int deleteUser(long id) {
    return userRepository.deleteUser(id);
  }
}
