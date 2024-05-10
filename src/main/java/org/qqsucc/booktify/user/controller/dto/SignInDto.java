package org.qqsucc.booktify.user.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

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

	@NotBlank
	@Length(min = 8, message = "Minimum password length - 8 characters")
	String password;

}
