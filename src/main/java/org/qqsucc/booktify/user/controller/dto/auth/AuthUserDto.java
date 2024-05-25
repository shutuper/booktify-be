package org.qqsucc.booktify.user.controller.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.qqsucc.booktify.user.controller.dto.UserDto;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
public class AuthUserDto {

	UserDto user;

	@Builder.Default
	boolean otpSent = false;

	@Schema(nullable = true)
	String accessToken;

	@Schema(nullable = true)
	String refreshToken;
}
