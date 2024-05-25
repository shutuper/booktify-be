package org.qqsucc.booktify.procedure.service;

import org.qqsucc.booktify.common.dto.TimeslotDto;
import org.qqsucc.booktify.procedure.controller.dto.AvailableDatesSearchDto;
import org.qqsucc.booktify.procedure.repository.entity.Procedure;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface TimeslotService {

	Map<LocalDate, List<TimeslotDto>> getAvailableTimeslots(AvailableDatesSearchDto datesSearchDto, Procedure procedure);

	boolean isAvailableTimeslot(TimeslotDto timeslotDto, Procedure procedure);

}
