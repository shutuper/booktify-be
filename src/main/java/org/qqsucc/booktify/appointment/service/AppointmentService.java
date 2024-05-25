package org.qqsucc.booktify.appointment.service;

import org.qqsucc.booktify.appointment.repository.entity.Appointment;
import org.qqsucc.booktify.appointment.service.domain.AppointmentFilter;
import org.qqsucc.booktify.common.dto.TimeslotDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public interface AppointmentService {

	Appointment findById(UUID appointmentId);

	Appointment save(Appointment appointment);

	Set<TimeslotDto> findAllBookedTimeslots(UUID masterId, Instant startDate, Instant endDate);

	Page<Appointment> findAllFilter(AppointmentFilter appointmentFilter, Pageable pageable);
}
