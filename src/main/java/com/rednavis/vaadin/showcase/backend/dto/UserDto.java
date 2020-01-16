package com.rednavis.vaadin.showcase.backend.dto;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

/**
 * UserDto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldNameConstants
public class UserDto {

  private long id;
  private String email;
  private String password;
  private Set<RoleDto> roleSet;
}
