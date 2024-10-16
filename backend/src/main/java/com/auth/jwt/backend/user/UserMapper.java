package com.auth.jwt.backend.user;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toUserDto(UserEntity user);
    
}
