package org.qqsucc.booktify.user.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.qqsucc.booktify.user.controller.dto.SignUpDto;
import org.qqsucc.booktify.user.controller.dto.UserDto;
import org.qqsucc.booktify.user.repository.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

	UserDto toUserDto(User user);

	@Mapping(target = "password", ignore = true)
	@Mapping(target = "isEmailVerified", constant = "false")
	@Mapping(target = "role", constant = "ROLE_MASTER")
	@Mapping(target = "status", constant = "ACTIVE")
	User toClientUser(SignUpDto signUpDto);

}
