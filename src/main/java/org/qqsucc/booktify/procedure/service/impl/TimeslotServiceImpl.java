package org.qqsucc.booktify.procedure.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.collections4.CollectionUtils;
import org.qqsucc.booktify.appointment.service.AppointmentService;
import org.qqsucc.booktify.common.dto.TimeslotDto;
import org.qqsucc.booktify.procedure.controller.dto.AvailableDatesSearchDto;
import org.qqsucc.booktify.procedure.repository.entity.Procedure;
import org.qqsucc.booktify.procedure.service.TimeslotService;
import org.qqsucc.booktify.workschedule.repository.entity.MasterWorkSchedule;
import org.qqsucc.booktify.workschedule.service.MasterWorkScheduleService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class TimeslotServiceImpl implements TimeslotService {

	MasterWorkScheduleService masterWorkScheduleService;
	AppointmentService appointmentService;

	@Override
	public Map<LocalDate, List<TimeslotDto>> getAvailableTimeslots(AvailableDatesSearchDto datesSearchDto, Procedure procedure) {
		List<MasterWorkSchedule> workSchedules = masterWorkScheduleService.findWorkSchedulesByMasterId(procedure.getMasterId());

		if (CollectionUtils.isEmpty(workSchedules) || procedure.isNotActive()) {
			return Collections.emptyMap();
		}

		ZoneId zoneId = ZoneId.of("Europe/Kiev");

		Set<TimeslotDto> allTimeslots = LocalDate.ofInstant(datesSearchDto.getStartDate(), zoneId).datesUntil(
						LocalDate.ofInstant(datesSearchDto.getEndDate(), zoneId).plusDays(1L) // +1 as exclusive
				)
				.flatMap(date -> workSchedules.stream()
						.filter(workSchedule -> workSchedule.isWorkingDay(date))
						.map(workSchedule -> toTimeslotDto(date, workSchedule, zoneId)))
				.collect(Collectors.toSet());

		Set<TimeslotDto> bookedTimeslots = appointmentService.findAllBookedTimeslots(
				procedure.getMasterId(), datesSearchDto.getStartDate(), datesSearchDto.getEndDate()
		);

		Set<TimeslotDto> availableTimeslots = splitByDuration(allTimeslots.stream()
				.map(timeslot -> timeslot.subtract(bookedTimeslots))
				.flatMap(Collection::stream)
				.collect(Collectors.toSet()), Long.valueOf(procedure.getDuration()));

		return availableTimeslots.stream()
				.filter(TimeslotDto::isPending)
				.collect(Collectors.groupingBy(timeslot -> LocalDate.ofInstant(timeslot.getStartDate(), zoneId)));
	}

	@Override
	public boolean isAvailableTimeslot(TimeslotDto timeslotDto, Procedure procedure) {
		AvailableDatesSearchDto datesSearchDto = new AvailableDatesSearchDto(
				timeslotDto.getStartDate(), timeslotDto.getEndDate()
		);
		return getAvailableTimeslots(datesSearchDto, procedure)
				.values()
				.stream()
				.flatMap(Collection::stream)
				.anyMatch(timeslotDto::equals);
	}

	private static TimeslotDto toTimeslotDto(LocalDate date, MasterWorkSchedule workSchedule, ZoneId zoneId) {
		Instant startDate = workSchedule.getStartTime()
				.atDate(date)
				.atZone(zoneId)
				.toInstant();
		Instant endDate = workSchedule.getEndTime()
				.atDate(date)
				.atZone(zoneId)
				.toInstant();

		return new TimeslotDto(startDate, endDate);
	}

	private Set<TimeslotDto> splitByDuration(Collection<TimeslotDto> timeslots, Long duration) {
		return timeslots.stream()
				.map(timeslot -> timeslot.splitByDuration(duration))
				.flatMap(Collection::stream)
				.collect(Collectors.toSet());
	}

	private Set<TimeslotDto> subtract(Collection<TimeslotDto> availableTimeslots, Set<TimeslotDto> busyTimeslots) {
		Set<TimeslotDto> result = new HashSet<>();

		nextTimeslot:
		for (TimeslotDto availableTimeslot : availableTimeslots) {
			for (TimeslotDto busyTimeslot : busyTimeslots) {
				if (availableTimeslot.isOverlapped(busyTimeslot)) {
					continue nextTimeslot;
				}
			}
			result.add(availableTimeslot);
		}

		return result;
	}

}
