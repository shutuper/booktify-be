package org.qqsucc.booktify.appointment.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.collections4.CollectionUtils;
import org.jetbrains.annotations.NotNull;
import org.qqsucc.booktify.appointment.repository.AppointmentRepository;
import org.qqsucc.booktify.appointment.repository.entity.Appointment;
import org.qqsucc.booktify.appointment.repository.entity.enumeration.AppointmentStatus;
import org.qqsucc.booktify.appointment.service.AppointmentService;
import org.qqsucc.booktify.appointment.service.domain.AppointmentFilter;
import org.qqsucc.booktify.common.dto.TimeslotDto;
import org.qqsucc.booktify.common.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class AppointmentServiceImpl implements AppointmentService {

	AppointmentRepository appointmentRepository;

	@Override
	public Appointment findById(UUID appointmentId) {
		return appointmentRepository
				.findById(appointmentId)
				.orElseThrow(() -> new NotFoundException("Appointment not found"));
	}

	@Override
	public Appointment save(Appointment appointment) {
		return appointmentRepository.save(appointment);
	}

	@Override
	public Set<TimeslotDto> findAllBookedTimeslots(UUID masterId, Instant startDate, Instant endDate) {
		return appointmentRepository.findAllBookedTimeslots(
				masterId, AppointmentStatus.ACTIVE, startDate, endDate
		);
	}

	@Override
	public Page<Appointment> findAllFilter(AppointmentFilter appointmentFilter, Pageable pageable) {
		return appointmentRepository.findAll(getAppointmentSpec(appointmentFilter), pageable);
	}

	@Override
	public List<Appointment> findAllForReminder() {
		return appointmentRepository.findAllByStatusAndIsNotifiedAndNotificationDateBefore(AppointmentStatus.ACTIVE, false, Instant.now());
	}

	@NotNull
	private static Specification<Appointment> getAppointmentSpec(AppointmentFilter appointmentFilter) {
		return Specification.where((Specification<Appointment>) (r, rq, cb) ->
						Optional.ofNullable(appointmentFilter.getMasterId())
								.map(masterId -> cb.equal(r.join("procedure").get("masterId"), masterId))
								.orElse(null)
				)
				.and((r, rq, cb) -> Optional.ofNullable(appointmentFilter.getClientId())
						.map(masterId -> cb.equal(r.get("clientId"), appointmentFilter.getClientId()))
						.orElse(null)
				)
				.and((r, rq, cb) -> CollectionUtils.isNotEmpty(appointmentFilter.getStatuses()) ?
						r.get("status").in(appointmentFilter.getStatuses()) : null);
	}
}
