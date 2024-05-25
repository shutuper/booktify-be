package org.qqsucc.booktify.user.controller.facade;

import org.qqsucc.booktify.user.controller.dto.*;
import org.qqsucc.booktify.user.controller.dto.auth.*;

public interface AuthFacade {

	UserDto signUpMaster(SignUpDto signUpDto);

	UserIdDto signUpClient(ClientDto clientDto);

	UserDto authMe();

	AuthUserDto refreshToken(RefreshTokenDto refreshTokenDto);

	AuthUserDto singIn(SignInDto signInDto);

	AuthUserDto confirmSingIn(SignInConfirmDto signInConfirmDto);
}
