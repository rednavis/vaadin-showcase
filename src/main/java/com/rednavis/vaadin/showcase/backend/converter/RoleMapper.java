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

  RoleDto entityToDto(RoleEntity roleEntity);

  RoleEntity dtoToEntity(RoleDto roleDto);

  Set<RoleDto> entitySetToDtoSet(Set<RoleEntity> roleEntitySet);

  Set<RoleEntity> dtoSetToEntitySet(Set<RoleDto> roleDtoSet);
}
