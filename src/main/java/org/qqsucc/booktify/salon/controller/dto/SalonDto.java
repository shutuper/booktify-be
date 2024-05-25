package org.qqsucc.booktify.salon.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

import java.time.Instant;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;
import static lombok.AccessLevel.PRIVATE;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
public class SalonDto {

	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	@JsonProperty(access = READ_ONLY)
	UUID id;

	@NotBlank
	String title;

	@NotBlank
	@Length(min = 3, max = 30)
	@Pattern(regexp = "^\\w+$", message = "Only 'a-z', 'A-Z', '0-9' and '_' characters allowed in link name")
	String linkName;

	@Schema(nullable = true)
	String description;

	@NotBlank
	String address;

	@Schema(nullable = true)
	UUID avatarId;

	@Schema(nullable = true)
	UUID bannerId;

	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	@JsonProperty(access = READ_ONLY)
	Instant createdDate;

}
