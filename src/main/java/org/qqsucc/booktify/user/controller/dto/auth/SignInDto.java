package org.qqsucc.booktify.user.controller.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
public class SignInDto {

	@NotBlank
	@Pattern(regexp = "380\\d{9}", message = "Invalid user phone number")
	String phone;

	@Schema(description = "OTP will be sent if password not provided")
	String password;

}
