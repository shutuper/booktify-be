package org.qqsucc.booktify.appointment.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.qqsucc.booktify.appointment.controller.dto.AppointmentDto;
import org.qqsucc.booktify.appointment.controller.dto.AppointmentRespDto;
import org.qqsucc.booktify.appointment.repository.entity.Appointment;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {

	AppointmentDto toDto(Appointment appointment);

	@Mapping(target = "salon", source = "appointment.procedure.master.salonMaster.salon")
	@Mapping(target = "master", source = "appointment.procedure.master")
	AppointmentRespDto toRespDto(Appointment appointment);

}
