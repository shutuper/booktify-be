package org.qqsucc.booktify.user.controller.facade.impl;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.qqsucc.booktify.common.util.SecurityUtils;
import org.qqsucc.booktify.user.controller.dto.AvatarUpdateDto;
import org.qqsucc.booktify.user.controller.facade.UserFacade;
import org.qqsucc.booktify.user.repository.entity.User;
import org.qqsucc.booktify.user.service.UserService;
import org.springframework.stereotype.Component;

import static lombok.AccessLevel.PRIVATE;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class UserFacadeImpl implements UserFacade {

	UserService userService;

	@Override
	public void updateAvatar(AvatarUpdateDto avatarDto) {
		User authUser = SecurityUtils.getAuthUser();
		authUser.setAvatarId(avatarDto.getAvatarId());
		userService.save(authUser);
	}

}
