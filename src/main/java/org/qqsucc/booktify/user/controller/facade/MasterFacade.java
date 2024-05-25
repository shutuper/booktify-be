package org.qqsucc.booktify.user.controller.facade;

import org.qqsucc.booktify.user.controller.dto.MasterDto;
import org.qqsucc.booktify.user.controller.dto.MasterPrivateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface MasterFacade {

	MasterDto getSalonMaster(UUID salonId, UUID masterId);

	Page<MasterDto> getSalonMasters(UUID salonId, Pageable pageable);

	Page<MasterPrivateDto> getSalonMastersPrivate(UUID salonId, Pageable pageable);
}
