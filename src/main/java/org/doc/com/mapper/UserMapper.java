package org.doc.com.mapper;

import org.doc.com.dto.UserCreateDto;
import org.doc.com.dto.UserDto;
import org.doc.com.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);

    @Mapping(target = "documents", ignore = true)
    User toEntity(UserDto userDto);

    @Mapping(target = "documents", ignore = true)
    @Mapping(target = "id", ignore = true)
    User userCreateDtoToEntity(UserCreateDto userCreateDto);
}