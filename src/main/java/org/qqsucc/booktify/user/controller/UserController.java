package org.qqsucc.booktify.user.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.qqsucc.booktify.user.controller.dto.AvatarUpdateDto;
import org.qqsucc.booktify.user.controller.facade.UserFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static lombok.AccessLevel.PRIVATE;

@Tag(name = "master")
@RestController
@RequestMapping("/api/v1/private")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class UserController {

	UserFacade userFacade;

	@PatchMapping("/users/avatar")
	public ResponseEntity<Void> updateAvatar(@RequestBody @Valid AvatarUpdateDto avatarDto) {
		userFacade.updateAvatar(avatarDto);
		return ResponseEntity.noContent().build();
	}

}
