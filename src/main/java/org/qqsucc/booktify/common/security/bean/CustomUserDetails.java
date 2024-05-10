package org.qqsucc.booktify.common.security.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.qqsucc.booktify.user.repository.entity.User;
import org.qqsucc.booktify.user.repository.entity.enums.UserRole;
import org.qqsucc.booktify.user.repository.entity.enums.UserStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
public class CustomUserDetails implements UserDetails {

	UUID id;

	String email;

	String firstname;

	String lastname;

	String password;

	UserRole role;

	UserStatus status;

	Collection<? extends GrantedAuthority> authorities;

	public static CustomUserDetails of(User user) {
		return CustomUserDetails.builder()
				.id(user.getId())
				.email(user.getEmail())
				.role(user.getRole())
				.status(user.getStatus())
				.password(user.getPassword())
				.firstname(user.getFirstname())
				.lastname(user.getLastname())
				.authorities(Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name())))
				.build();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return status == UserStatus.ACTIVE;
	}

}
