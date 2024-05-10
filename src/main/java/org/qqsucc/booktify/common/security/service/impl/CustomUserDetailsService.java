package org.qqsucc.booktify.common.security.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.qqsucc.booktify.common.security.bean.CustomUserDetails;
import org.qqsucc.booktify.user.repository.entity.User;
import org.qqsucc.booktify.user.service.UserService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class CustomUserDetailsService implements UserDetailsService {

	UserService userService;

	@Override
	public CustomUserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
		User user = userService
				.findOptByPhone(phone)
				.orElseThrow(() -> new UsernameNotFoundException("User not found by phone number"));

		return CustomUserDetails.of(user);
	}

}
