package org.qqsucc.booktify.appointment.service.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.qqsucc.booktify.appointment.repository.entity.enumeration.AppointmentStatus;

import java.util.Set;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = PRIVATE)
public class AppointmentFilter {

	UUID masterId;

	UUID clientId;

	Set<AppointmentStatus> statuses;

}
