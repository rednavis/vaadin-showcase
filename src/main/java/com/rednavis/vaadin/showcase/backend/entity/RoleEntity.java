package com.rednavis.vaadin.showcase.backend.entity;

import com.rednavis.vaadin.showcase.backend.enums.UserRole;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

/**
 * RoleEntity.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldNameConstants
public class RoleEntity {

  private Long id;
  @NotNull
  private UserRole role;
}
