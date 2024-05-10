package org.qqsucc.booktify.user.controller.facade;

import org.qqsucc.booktify.user.controller.dto.*;

import java.util.List;

public interface UserFacade {

	List<UserDto> findAll();

	AuthUserDto signUpMaster(SignUpDto signUpDto);

	UserDto authMe();

	AuthUserDto refreshToken(RefreshTokenDto refreshTokenDto);

	AuthUserDto singIn(SignInDto signInDto);
}
