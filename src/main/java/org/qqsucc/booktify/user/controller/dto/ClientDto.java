package org.qqsucc.booktify.user.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;
import static lombok.AccessLevel.PRIVATE;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
public class ClientDto {

	@JsonProperty(access = READ_ONLY)
	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	UUID id;

	@NotBlank
	String firstname;

	@NotBlank
	String lastname;

	@NotBlank
	@Pattern(regexp = "380\\d{9}", message = "Invalid user phone number")
	String phone;

	@JsonProperty(access = READ_ONLY)
	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	UUID avatarId;

}
