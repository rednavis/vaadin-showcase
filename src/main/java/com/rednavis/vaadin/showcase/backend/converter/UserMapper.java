package com.rednavis.vaadin.showcase.backend.converter;

import com.rednavis.vaadin.showcase.backend.dto.UserDto;
import com.rednavis.vaadin.showcase.backend.entity.UserEntity;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

  UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
  
  UserDto entityToDto(UserEntity userEntity);

  UserEntity dtoToEntity(UserDto stateDto);

  List<UserDto> entityListToDtoList(List<UserEntity> userEntityList);

  List<UserEntity> dtoListToEntityList(List<UserDto> userDtoList);
}