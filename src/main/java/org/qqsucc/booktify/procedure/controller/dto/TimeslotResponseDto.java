package org.qqsucc.booktify.procedure.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.qqsucc.booktify.common.dto.TimeslotDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = PRIVATE)
public class TimeslotResponseDto {

	LocalDate searchDate;

	@Builder.Default
	List<TimeslotDto> timeslots = new ArrayList<>();

}
