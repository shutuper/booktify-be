package org.qqsucc.booktify.workschedule.service;

import org.qqsucc.booktify.workschedule.repository.entity.MasterWorkSchedule;

import java.util.List;
import java.util.UUID;

public interface MasterWorkScheduleService {

	List<MasterWorkSchedule> findWorkSchedulesByMasterId(UUID masterId);

	void deleteAllByMasterId(UUID masterId);

	List<MasterWorkSchedule> saveAll(List<MasterWorkSchedule> workSchedules);
}
