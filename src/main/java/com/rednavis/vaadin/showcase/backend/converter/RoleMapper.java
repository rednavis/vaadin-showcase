package com.rednavis.vaadin.showcase.backend.converter;

import com.rednavis.vaadin.showcase.backend.dto.RoleDto;
import com.rednavis.vaadin.showcase.backend.entity.RoleEntity;
import java.util.Set;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * RoleMapper.
 */
@Mapper
public interface RoleMapper {

  RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

  RoleDto roleEntityToRoleDto(RoleEntity roleEntity);

  RoleEntity roleDtoToRoleEntity(RoleDto roleDto);

  Set<RoleDto> setRoleEntityToListRoleDto(Set<RoleEntity> roleEntityList);

  Set<RoleEntity> setRoleDtoToListRoleEntity(Set<RoleDto> roleDtoList);
}
