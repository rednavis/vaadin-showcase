package com.rednavis.vaadin.showcase.backend;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.rednavis.vaadin.showcase.backend.dto.RoleDto;
import com.rednavis.vaadin.showcase.backend.dto.UserDto;
import com.rednavis.vaadin.showcase.backend.enums.UserRole;
import com.rednavis.vaadin.showcase.backend.service.user.UserRepositoryImpl;
import com.rednavis.vaadin.showcase.backend.service.user.UserService;
import com.rednavis.vaadin.showcase.backend.service.user.UserServiceImpl;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled
public class UserServiceTest {

  private static UserService userService;

  @BeforeAll
  public static void init() {
    userService = new UserServiceImpl(new UserRepositoryImpl());
  }

  @Test
  public void shouldUserServiceMethods() {
    Set<RoleDto> userRoles = new HashSet<>(Collections.singleton(RoleDto.builder()
        .id(2L)
        .role(UserRole.USER)
        .build()));
    UserDto userDto = UserDto.builder()
        .email("email")
        .password("password")
        .roleSet(userRoles)
        .build();

    long userId = userService.createUser(userDto);
    userDto.setId(userId);
    assertEquals(userDto, userService.getUserById(userId));
    assertEquals(userDto, userService.getUserByEmail("test_email"));

    Set<RoleDto> updatedUserRoles = new HashSet<>(Collections.singleton(RoleDto.builder()
        .id(1L)
        .role(UserRole.ADMIN)
        .build()));
    UserDto updatedUserDto = UserDto.builder()
        .id(userDto.getId())
        .email("new_email")
        .roleSet(updatedUserRoles)
        .build();
    assertEquals(1, userService.updateUser(updatedUserDto));
    assertEquals(updatedUserDto, userService.getUserById(updatedUserDto.getId()));
    assertEquals(1, userService.deleteUser(updatedUserDto.getId()));
  }
}
