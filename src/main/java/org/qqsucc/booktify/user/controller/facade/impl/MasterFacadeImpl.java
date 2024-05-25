package org.qqsucc.booktify.user.controller.facade.impl;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.qqsucc.booktify.user.controller.dto.MasterDto;
import org.qqsucc.booktify.user.controller.dto.MasterPrivateDto;
import org.qqsucc.booktify.user.controller.facade.MasterFacade;
import org.qqsucc.booktify.user.controller.mapper.UserMapper;
import org.qqsucc.booktify.user.repository.entity.User;
import org.qqsucc.booktify.user.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class MasterFacadeImpl implements MasterFacade {

	UserService userService;
	UserMapper userMapper;

	@Override
	public MasterDto getSalonMaster(UUID salonId, UUID masterId) {
		User master = userService.findBySalonIdAndMasterId(salonId, masterId);
		return userMapper.toMasterDto(master);
	}

	@Override
	public Page<MasterDto> getSalonMasters(UUID salonId, Pageable pageable) {
		return userService
				.findAllMastersBySalonId(salonId, pageable)
				.map(userMapper::toMasterDto);
	}

	@Override
	public Page<MasterPrivateDto> getSalonMastersPrivate(UUID salonId, Pageable pageable) {
		return userService
				.findAllMastersBySalonId(salonId, pageable)
				.map(userMapper::toMasterPrivateDto);
	}
}
