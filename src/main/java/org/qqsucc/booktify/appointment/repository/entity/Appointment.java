package org.qqsucc.booktify.appointment.repository.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.Nullable;
import org.qqsucc.booktify.appointment.repository.entity.enumeration.AppointmentStatus;
import org.qqsucc.booktify.common.entity.BaseEntity;
import org.qqsucc.booktify.procedure.repository.entity.Procedure;
import org.qqsucc.booktify.user.repository.entity.User;

import java.time.Instant;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Table(name = "appointment")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = PRIVATE)
public class Appointment extends BaseEntity {

	@Builder.Default
	@Enumerated(EnumType.STRING)
	AppointmentStatus status = AppointmentStatus.ACTIVE;

	Instant startDate;

	Instant endDate;

	@PositiveOrZero
	Double price;

	@Column(name = "procedure_id")
	UUID procedureId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "procedure_id", insertable = false, updatable = false)
	Procedure procedure;

	@Column(name = "client_id")
	UUID clientId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "client_id", insertable = false, updatable = false)
	User client;

	@Nullable
	Instant notificationDate;

	@Builder.Default
	Boolean isNotified = false;

	@Nullable
	Instant canceledDate;

	@Nullable
	String canceledReason;

	@Builder.Default
	Instant createdDate = Instant.now();

}
