package org.qqsucc.booktify.user.controller.facade.impl;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.StringUtils;
import org.qqsucc.booktify.common.exception.AlreadyExistException;
import org.qqsucc.booktify.common.exception.BusinessException;
import org.qqsucc.booktify.common.security.bean.TokenType;
import org.qqsucc.booktify.common.security.service.JwtTokenService;
import org.qqsucc.booktify.common.util.SecurityUtils;
import org.qqsucc.booktify.user.controller.dto.*;
import org.qqsucc.booktify.user.controller.facade.UserFacade;
import org.qqsucc.booktify.user.controller.mapper.UserMapper;
import org.qqsucc.booktify.user.repository.entity.User;
import org.qqsucc.booktify.user.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static lombok.AccessLevel.PRIVATE;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class UserFacadeImpl implements UserFacade {

	PasswordEncoder passwordEncoder;
	JwtTokenService jwtTokenService;
	UserService userService;

	UserMapper userMapper;

	@Override
	public List<UserDto> findAll() {
		return userService.findAll().stream().map(userMapper::toUserDto).toList();
	}

	@Override
	@Transactional
	public AuthUserDto signUpMaster(SignUpDto signUpDto) {
		throwIfUserExistsByPhoneOrEmail(signUpDto.getPhone(), signUpDto.getEmail());

		User user = userMapper.toClientUser(signUpDto);
		user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

		return getAuthUserDto(userService.save(user), EnumSet.of(TokenType.ACCESS, TokenType.REFRESH));
	}

	@Override
	public UserDto authMe() {
		return userMapper.toUserDto(SecurityUtils.getAuthUser());
	}

	@Override
	public AuthUserDto refreshToken(RefreshTokenDto refreshTokenDto) {
		String userPhone = jwtTokenService.parseJwsClaims(refreshTokenDto.getRefreshToken(), TokenType.REFRESH)
				.getPayload()
				.getSubject();
		return getAuthUserDto(userService.findByPhone(userPhone), EnumSet.of(TokenType.ACCESS));
	}

	@Override
	public AuthUserDto singIn(SignInDto signInDto) {
		return userService
				.findOptByPhone(signInDto.getPhone())
				.filter(user -> passwordEncoder.matches(signInDto.getPassword(), user.getPassword()))
				.map(user -> getAuthUserDto(user, EnumSet.of(TokenType.ACCESS, TokenType.REFRESH)))
				.orElseThrow(() -> new BusinessException("User with specified credentials not found"));
	}

	private void throwIfUserExistsByPhoneOrEmail(String phone, String email) {
		boolean existsByPhone = userService.existsByPhone(phone);

		if (existsByPhone) {
			throw new AlreadyExistException("Provided phone number is already in use");
		}

		boolean existsByEmail = StringUtils.isNotBlank(email) && userService.existsByEmail(email);

		if (existsByEmail) {
			throw new AlreadyExistException("Provided email is already in use");
		}
	}

	private AuthUserDto getAuthUserDto(User user, Set<TokenType> tokenTypes) {
		Map<TokenType, String> authTokens = jwtTokenService.generateTokens(user.getPhone(), tokenTypes);

		return AuthUserDto.builder()
				.user(userMapper.toUserDto(user))
				.accessToken(authTokens.get(TokenType.ACCESS))
				.refreshToken(authTokens.get(TokenType.REFRESH))
				.build();
	}

}
