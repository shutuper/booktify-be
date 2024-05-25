package org.qqsucc.booktify.appointment.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = PRIVATE)
public class AppointmentReqDto {

	@NotNull
	Instant startDate;

	@NotNull
	Instant endDate;

	@NotNull
	UUID procedureId;

	@Schema(nullable = true)
	Instant notificationDate;

}
