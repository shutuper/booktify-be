package org.qqsucc.booktify.workschedule.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.qqsucc.booktify.workschedule.repository.MasterWorkScheduleRepository;
import org.qqsucc.booktify.workschedule.repository.entity.MasterWorkSchedule;
import org.qqsucc.booktify.workschedule.service.MasterWorkScheduleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class MasterWorkScheduleServiceImpl implements MasterWorkScheduleService {

	MasterWorkScheduleRepository masterWorkScheduleRepository;

	@Override
	public List<MasterWorkSchedule> findWorkSchedulesByMasterId(UUID masterId) {
		return masterWorkScheduleRepository.findAllByMasterId(masterId);
	}

	@Override
	@Transactional
	public void deleteAllByMasterId(UUID masterId) {
		masterWorkScheduleRepository.deleteAllByMasterId(masterId);
	}

	@Override
	public List<MasterWorkSchedule> saveAll(List<MasterWorkSchedule> workSchedules) {
		return masterWorkScheduleRepository.saveAll(workSchedules);
	}
}
