package org.qqsucc.booktify.appointment.controller.facade;

import org.qqsucc.booktify.appointment.controller.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface AppointmentFacade {

	Page<AppointmentRespDto> getAppointments(Pageable pageable);

	AppointmentDto bookAppointment(AppointmentReqDto appointmentReqDto);

	AppointmentDto cancelAppointment(UUID appointmentId, AppointmentCancelDto cancelDto);

	AppointmentDto bookAppointmentByMaster(AppointmentMasterReqDto appointmentReqDto);
}
