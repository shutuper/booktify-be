package org.qqsucc.booktify.user.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.qqsucc.booktify.user.controller.dto.ClientDto;
import org.qqsucc.booktify.user.controller.dto.MasterDto;
import org.qqsucc.booktify.user.controller.dto.MasterPrivateDto;
import org.qqsucc.booktify.user.controller.dto.auth.SignUpDto;
import org.qqsucc.booktify.user.controller.dto.UserDto;
import org.qqsucc.booktify.user.repository.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

	UserDto toUserDto(User user);

	MasterDto toMasterDto(User user);

	MasterPrivateDto toMasterPrivateDto(User user);

	@Mapping(target = "password", ignore = true)
	@Mapping(target = "isEmailVerified", constant = "false")
	@Mapping(target = "role", constant = "ROLE_MASTER")
	@Mapping(target = "status", constant = "NOT_ACTIVE")
	User toMasterUser(SignUpDto signUpDto);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "avatarId", ignore = true)
	@Mapping(target = "password", ignore = true)
	@Mapping(target = "email", ignore = true)
	@Mapping(target = "isEmailVerified", constant = "false")
	@Mapping(target = "role", constant = "ROLE_CLIENT")
	@Mapping(target = "status", constant = "ACTIVE")
	User toClientUser(ClientDto clientDto);

}
