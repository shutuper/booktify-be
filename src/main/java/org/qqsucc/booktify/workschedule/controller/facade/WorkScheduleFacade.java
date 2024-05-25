package org.qqsucc.booktify.workschedule.controller.facade;

import org.qqsucc.booktify.workschedule.controller.dto.MultiWorkScheduleDto;

public interface WorkScheduleFacade {

	MultiWorkScheduleDto getMasterWorkSchedule();

	MultiWorkScheduleDto putMasterWorkSchedule(MultiWorkScheduleDto workSchedulesDto);

}
