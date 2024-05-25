package org.qqsucc.booktify.appointment.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.qqsucc.booktify.procedure.controller.dto.ProcedureDto;
import org.qqsucc.booktify.salon.controller.dto.SalonDto;
import org.qqsucc.booktify.user.controller.dto.ClientDto;
import org.qqsucc.booktify.user.controller.dto.MasterDto;

import static lombok.AccessLevel.PRIVATE;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = PRIVATE)
public class AppointmentRespDto extends AppointmentDto {

	ClientDto client;

	MasterDto master;

	ProcedureDto procedure;

	SalonDto salon;

}
