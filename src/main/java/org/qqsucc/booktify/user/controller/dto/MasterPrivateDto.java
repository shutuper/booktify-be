package org.qqsucc.booktify.user.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
public class MasterPrivateDto {

	UUID id;

	String firstname;

	String lastname;

	String phone;

	@Schema(nullable = true)
	String email;

	@Schema(nullable = true)
	UUID avatarId;
}
