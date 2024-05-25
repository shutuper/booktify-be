package org.qqsucc.booktify.appointment.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.qqsucc.booktify.appointment.repository.entity.enumeration.AppointmentStatus;

import java.time.Instant;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = PRIVATE)
public class AppointmentDto {

	UUID id;

	AppointmentStatus status;

	Instant startDate;

	Instant endDate;

	UUID procedureId;

	Double price;

	UUID clientId;

	@Schema(nullable = true)
	Instant canceledDate;

	@Schema(nullable = true)
	String canceledReason;

	Instant createdDate;

}
