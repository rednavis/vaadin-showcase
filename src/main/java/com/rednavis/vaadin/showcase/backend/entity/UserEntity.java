package com.rednavis.vaadin.showcase.backend.entity;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

/**
 * UserEntity.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldNameConstants
public class UserEntity {

  private long id;
  private String email;
  private String password;
  private Set<RoleEntity> roleSet;
}
