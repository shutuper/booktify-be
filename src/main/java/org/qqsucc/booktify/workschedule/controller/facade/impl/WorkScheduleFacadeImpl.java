package org.qqsucc.booktify.workschedule.controller.facade.impl;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.qqsucc.booktify.common.exception.BusinessException;
import org.qqsucc.booktify.common.util.SecurityUtils;
import org.qqsucc.booktify.workschedule.controller.dto.MultiWorkScheduleDto;
import org.qqsucc.booktify.workschedule.controller.dto.WorkScheduleDto;
import org.qqsucc.booktify.workschedule.controller.facade.WorkScheduleFacade;
import org.qqsucc.booktify.workschedule.controller.mapper.WorkScheduleMapper;
import org.qqsucc.booktify.workschedule.repository.entity.MasterWorkSchedule;
import org.qqsucc.booktify.workschedule.service.MasterWorkScheduleService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PRIVATE;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class WorkScheduleFacadeImpl implements WorkScheduleFacade {

	MasterWorkScheduleService masterWorkScheduleService;
	WorkScheduleMapper workScheduleMapper;

	@Override
	public MultiWorkScheduleDto getMasterWorkSchedule() {
		List<WorkScheduleDto> workSchedules = masterWorkScheduleService.findWorkSchedulesByMasterId(
						SecurityUtils.getAuthUserId()
				)
				.stream()
				.map(workScheduleMapper::toDto)
				.sorted(Comparator.comparing(WorkScheduleDto::getStartTime))
				.collect(Collectors.toList());

		return MultiWorkScheduleDto.builder()
				.workSchedules(workSchedules)
				.build();
	}

	@Override
	@Transactional
	public MultiWorkScheduleDto putMasterWorkSchedule(MultiWorkScheduleDto workSchedulesDto) {
		boolean invalidWorkSchedule = workSchedulesDto.getWorkSchedules()
				.stream()
				.anyMatch(workSchedule -> workSchedule.getStartTime().isAfter(workSchedule.getEndTime()));

		if (invalidWorkSchedule) {
			throw new BusinessException("Time slot end time can not be less than start time");
		}

		UUID masterId = SecurityUtils.getAuthUserId();
		masterWorkScheduleService.deleteAllByMasterId(masterId);

		List<MasterWorkSchedule> workSchedulesToSave = workSchedulesDto.getWorkSchedules()
				.stream()
				.map(workScheduleDto -> workScheduleMapper.toMasterWorkSchedule(masterId, workScheduleDto))
				.collect(Collectors.toList());

		List<MasterWorkSchedule> workSchedules = masterWorkScheduleService.saveAll(workSchedulesToSave);

		return MultiWorkScheduleDto.builder()
				.workSchedules(workSchedules.stream().map(workScheduleMapper::toDto)
						.sorted(Comparator.comparing(WorkScheduleDto::getStartTime))
						.collect(Collectors.toList()))
				.build();
	}
}
