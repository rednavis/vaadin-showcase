package com.rednavis.vaadin.showcase.backend.dto;

import com.rednavis.vaadin.showcase.backend.enums.UserRole;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

/**
 * RoleDto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldNameConstants
public class RoleDto {

  private Long id;
  @NotNull
  private UserRole role;
}
