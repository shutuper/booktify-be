package org.qqsucc.booktify.workschedule.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.qqsucc.booktify.workschedule.controller.dto.WorkScheduleDto;
import org.qqsucc.booktify.workschedule.repository.entity.MasterWorkSchedule;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface WorkScheduleMapper {

	WorkScheduleDto toDto(MasterWorkSchedule entity);

	@Mapping(target = "master", ignore = true)
	MasterWorkSchedule toMasterWorkSchedule(UUID masterId, WorkScheduleDto workScheduleDto);
}
