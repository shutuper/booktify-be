package org.qqsucc.booktify.user.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.qqsucc.booktify.user.controller.dto.*;
import org.qqsucc.booktify.user.controller.dto.auth.*;
import org.qqsucc.booktify.user.controller.facade.AuthFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static lombok.AccessLevel.PRIVATE;

@Tag(name = "user")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class AuthController {

	AuthFacade authFacade;

	@PostMapping("/public/users/masters/sign-up")
	public ResponseEntity<UserDto> signUpMaster(@RequestBody @Valid SignUpDto signUpDto) {
		return ResponseEntity.ok(authFacade.signUpMaster(signUpDto));
	}

	@PostMapping("/public/users/clients/sign-up")
	public ResponseEntity<UserIdDto> signUpClient(@RequestBody @Valid ClientDto clientDto) {
		return ResponseEntity.ok(authFacade.signUpClient(clientDto));
	}

	@PostMapping("/public/users/sign-in/step-1") // step 1
	public ResponseEntity<AuthUserDto> singIn(@RequestBody @Valid SignInDto signInDto) {
		AuthUserDto response = authFacade.singIn(signInDto);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/public/users/sign-in/step-2") // step 2
	public ResponseEntity<AuthUserDto> confirmSingIn(@RequestBody @Valid SignInConfirmDto signInConfirmDto) {
		AuthUserDto response = authFacade.confirmSingIn(signInConfirmDto);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/private/users/me")
	public ResponseEntity<UserDto> authMe() {
		return ResponseEntity.ok(authFacade.authMe());
	}

	@PostMapping("/public/users/refresh-token")
	public ResponseEntity<AuthUserDto> refreshToken(@RequestBody @Valid RefreshTokenDto refreshTokenDto) {
		return ResponseEntity.ok(authFacade.refreshToken(refreshTokenDto));
	}

}
