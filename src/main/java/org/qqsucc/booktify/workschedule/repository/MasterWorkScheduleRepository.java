package org.qqsucc.booktify.workschedule.repository;

import org.qqsucc.booktify.workschedule.repository.entity.MasterWorkSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;
import java.util.UUID;

public interface MasterWorkScheduleRepository extends JpaRepository<MasterWorkSchedule, UUID> {

	List<MasterWorkSchedule> findAllByMasterId(UUID masterId);

	@Modifying
	void deleteAllByMasterId(UUID masterId);
}
