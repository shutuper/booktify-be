package org.qqsucc.booktify.procedure.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.qqsucc.booktify.procedure.repository.entity.enumeration.ProcedureStatus;

import java.time.Instant;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;
import static lombok.AccessLevel.PRIVATE;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = PRIVATE)
public class ProcedureDto {

	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	@JsonProperty(access = READ_ONLY)
	UUID id;

	@NotBlank
	String title;

	@NotBlank
	String description;

	ProcedureStatus status;

	@NotNull
	@Min(5) // - 5 min
	@Max(720) // - 12 hours
	Integer duration; // in minutes

	@PositiveOrZero
	@NotNull
	Double price;

	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	@JsonProperty(access = READ_ONLY)
	UUID masterId;

	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	@JsonProperty(access = READ_ONLY)
	Instant createdDate = Instant.now();

}
