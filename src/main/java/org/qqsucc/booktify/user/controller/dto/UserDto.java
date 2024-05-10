package org.qqsucc.booktify.user.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.StringUtils;
import org.qqsucc.booktify.user.repository.entity.enums.UserRole;
import org.qqsucc.booktify.user.repository.entity.enums.UserStatus;

import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;
import static lombok.AccessLevel.PRIVATE;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
public class UserDto {

	@JsonProperty(access = READ_ONLY)
	UUID id;

	@JsonProperty(access = READ_ONLY)
	@Enumerated(EnumType.STRING)
	UserRole role;

	@JsonProperty(access = READ_ONLY)
	@Enumerated(EnumType.STRING)
	UserStatus status;

	String firstname;

	String lastname;

	@JsonProperty(access = READ_ONLY)
	@Schema(nullable = true)
	@Email(message = "Invalid user email")
	String email;

	@JsonProperty(access = READ_ONLY)
	Boolean isEmailVerified;

	@JsonProperty(access = READ_ONLY)
	@Pattern(regexp = "380\\d{9}", message = "Invalid user phone number")
	String phone;

	public String getEmail() {
		return StringUtils.lowerCase(email);
	}
}
