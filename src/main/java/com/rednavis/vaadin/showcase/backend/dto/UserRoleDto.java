package com.rednavis.vaadin.showcase.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

/**
 * UserRoleDto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldNameConstants
public class UserRoleDto {

  private long userId;
  private long roleId;
}
