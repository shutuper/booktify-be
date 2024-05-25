package org.qqsucc.booktify.workschedule.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;
import static lombok.AccessLevel.PRIVATE;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = PRIVATE)
public class WorkScheduleDto {

	@JsonProperty(access = READ_ONLY)
	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	UUID id;

	@NotNull
	DayOfWeek dayOfWeek;

	@NotNull
	LocalTime startTime;

	@NotNull
	LocalTime endTime;

	public LocalTime getStartTime() {
		return startTime.withSecond(0);
	}

	public LocalTime getEndTime() {
		return endTime.withSecond(0);
	}
}
