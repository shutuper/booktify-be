package org.qqsucc.booktify.user.controller.facade.impl;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.qqsucc.booktify.common.exception.AlreadyExistException;
import org.qqsucc.booktify.common.exception.BusinessException;
import org.qqsucc.booktify.common.security.bean.TokenType;
import org.qqsucc.booktify.common.security.service.JwtTokenService;
import org.qqsucc.booktify.common.util.SecurityUtils;
import org.qqsucc.booktify.notification.service.NotificationService;
import org.qqsucc.booktify.salon.repository.entity.SalonMaster;
import org.qqsucc.booktify.salon.repository.entity.SalonMasterInvite;
import org.qqsucc.booktify.salon.service.SalonMasterService;
import org.qqsucc.booktify.user.controller.dto.ClientDto;
import org.qqsucc.booktify.user.controller.dto.UserDto;
import org.qqsucc.booktify.user.controller.dto.UserIdDto;
import org.qqsucc.booktify.user.controller.dto.auth.*;
import org.qqsucc.booktify.user.controller.facade.AuthFacade;
import org.qqsucc.booktify.user.controller.mapper.UserMapper;
import org.qqsucc.booktify.user.repository.entity.User;
import org.qqsucc.booktify.user.repository.entity.UserCode;
import org.qqsucc.booktify.user.repository.entity.enums.UserStatus;
import org.qqsucc.booktify.user.service.UserCodeService;
import org.qqsucc.booktify.user.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static lombok.AccessLevel.PRIVATE;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class AuthFacadeImpl implements AuthFacade {

	NotificationService notificationService;
	SalonMasterService salonMasterService;
	PasswordEncoder passwordEncoder;
	JwtTokenService jwtTokenService;
	UserCodeService userCodeService;
	UserService userService;

	UserMapper userMapper;

	private static final EnumSet<TokenType> ACCESS_REFRESH_TOKENS = EnumSet.of(TokenType.ACCESS, TokenType.REFRESH);

	@Override
	@Transactional
	public UserDto signUpMaster(SignUpDto signUpDto) {
		throwIfUserExistsByPhoneOrEmail(signUpDto.getPhone(), signUpDto.getEmail());

		User user = userMapper.toMasterUser(signUpDto);
		user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

		User savedMaster = userService.save(user);

		saveMasterSalonFromInvite(signUpDto.getInviteToken(), savedMaster);

		return userMapper.toUserDto(savedMaster);
	}

	@Override
	@Transactional
	public UserIdDto signUpClient(ClientDto clientDto) {
		Optional<User> existedUser = userService.findOptByPhone(clientDto.getPhone());

		if (existedUser.isPresent() && !existedUser.get().isClient()) {
			throw new AlreadyExistException("Entered phone number is not available");
		}

		User client = existedUser.orElseGet(() -> {
			User newClient = userMapper.toClientUser(clientDto);
			newClient.setStatus(UserStatus.NOT_ACTIVE);
			newClient.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
			return userService.save(newClient);
		});

		UserCode userCode = userCodeService.generateCode(client.getId());
		notificationService.sendSmsOtpCode(client.getPhone(), userCode.getCode());

		return new UserIdDto(client.getId());
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
		return getAuthUserDto(userService.findByPhone(userPhone), EnumSet.of(TokenType.ACCESS, TokenType.REFRESH), false);
	}

	@Override
	public AuthUserDto singIn(SignInDto signInDto) {
		User user = userService
				.findOptByPhone(signInDto.getPhone())
				.orElseThrow(() -> new BusinessException("User with specified credentials not found"));

		boolean sendOtpCode = user.isNotActive() || StringUtils.isBlank(signInDto.getPassword());

		if (sendOtpCode) {
			UserCode userCode = userCodeService.generateCode(user.getId());
			notificationService.sendSmsOtpCode(user.getPhone(), userCode.getCode());

		} else if (!passwordEncoder.matches(signInDto.getPassword(), user.getPassword())) {
			throw new BusinessException("User with specified credentials not found");
		}

		Set<TokenType> tokenTypes = sendOtpCode ? null : ACCESS_REFRESH_TOKENS;

		return getAuthUserDto(user, tokenTypes, sendOtpCode);
	}

	@Override
	public AuthUserDto confirmSingIn(SignInConfirmDto signInConfirmDto) {
		User user = userCodeService.verifyCode(signInConfirmDto.getUserId(), signInConfirmDto.getCode());

		if (user.isNotActive()) {
			user.setStatus(UserStatus.ACTIVE);
			User activatedUser = userService.save(user);
			return getAuthUserDto(activatedUser, ACCESS_REFRESH_TOKENS, false);
		}

		return getAuthUserDto(user, ACCESS_REFRESH_TOKENS, false);
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

	private AuthUserDto getAuthUserDto(User user, Set<TokenType> tokenTypes, boolean otpSent) {
		Map<TokenType, String> authTokens = CollectionUtils.isEmpty(tokenTypes) ? new HashMap<>() :
				jwtTokenService.generateTokens(user.getPhone(), tokenTypes);

		return AuthUserDto.builder()
				.user(userMapper.toUserDto(user))
				.accessToken(authTokens.get(TokenType.ACCESS))
				.refreshToken(authTokens.get(TokenType.REFRESH))
				.otpSent(otpSent)
				.build();
	}

	private void saveMasterSalonFromInvite(String inviteToken, User master) {
		if (StringUtils.isBlank(inviteToken)) {
			return;
		}

		SalonMasterInvite masterInvite = salonMasterService.findByInviteToken(inviteToken);

		salonMasterService.save(
				SalonMaster.builder()
						.masterId(master.getId())
						.salonId(masterInvite.getSalonId())
						.build()
		);

		salonMasterService.deleteMasterInviteById(masterInvite.getId());
	}

}
