package org.qqsucc.booktify.user.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
public class AuthUserDto {

	UserDto user;

	String accessToken;

	@Schema(nullable = true, description = "Not null only in sign-in/sign-up responses")
	String refreshToken;
}
