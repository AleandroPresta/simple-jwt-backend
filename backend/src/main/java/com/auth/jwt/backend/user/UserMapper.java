package com.auth.jwt.backend.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.auth.jwt.backend.signup.SignUpDto;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toUserDto(UserEntity user);

    @Mapping(target = "password", ignore = true)
    UserEntity signUpToUser(SignUpDto signUpDto);
}
