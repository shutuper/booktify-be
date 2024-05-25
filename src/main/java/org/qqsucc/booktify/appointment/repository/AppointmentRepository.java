package org.qqsucc.booktify.appointment.repository;

import org.jetbrains.annotations.NotNull;
import org.qqsucc.booktify.appointment.repository.entity.Appointment;
import org.qqsucc.booktify.appointment.repository.entity.enumeration.AppointmentStatus;
import org.qqsucc.booktify.common.dto.TimeslotDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public interface AppointmentRepository extends JpaRepository<Appointment, UUID>, JpaSpecificationExecutor<Appointment> {

	@Query("""
			select new org.qqsucc.booktify.common.dto.TimeslotDto(a.startDate, a.endDate) from Appointment a
			where a.procedure.masterId = ?1 and a.status = ?2 and a.endDate >= ?3 and a.startDate <= ?4
			""")
	Set<TimeslotDto> findAllBookedTimeslots(UUID masterId, AppointmentStatus status, Instant startSearchDate, Instant endSearchDate);

	@NotNull
	@EntityGraph(attributePaths = {"client", "procedure.master.salonMaster.salon"})
	Page<Appointment> findAll(@NotNull Specification<Appointment> spec, @NotNull Pageable pageable);

}
