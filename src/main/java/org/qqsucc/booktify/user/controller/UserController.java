package org.qqsucc.booktify.user.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.qqsucc.booktify.user.controller.dto.*;
import org.qqsucc.booktify.user.controller.facade.UserFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Tag(name = "user")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class UserController {

	UserFacade userFacade;

	@GetMapping("/private/users")
	public ResponseEntity<List<UserDto>> findAll() {
		return ResponseEntity.ok(userFacade.findAll());
	}

	@PostMapping("/public/users/masters/sign-up")
	public ResponseEntity<AuthUserDto> signUpMaster(@RequestBody @Valid SignUpDto signUpDto) {
		return ResponseEntity.ok(userFacade.signUpMaster(signUpDto));
	}

	@PostMapping("/public/users/sign-in")
	public ResponseEntity<AuthUserDto> singIn(@RequestBody @Valid SignInDto signInDto) {
		return ResponseEntity.ok(userFacade.singIn(signInDto));
	}

	@GetMapping("/private/users/me")
	public ResponseEntity<UserDto> authMe() {
		return ResponseEntity.ok(userFacade.authMe());
	}

	@PostMapping("/public/users/refresh-token")
	public ResponseEntity<AuthUserDto> refreshToken(@RequestBody @Valid RefreshTokenDto refreshTokenDto) {
		return ResponseEntity.ok(userFacade.refreshToken(refreshTokenDto));
	}

}
