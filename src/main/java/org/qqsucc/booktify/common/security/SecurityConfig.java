package org.qqsucc.booktify.common.security;

import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.qqsucc.booktify.common.security.exhandlers.CustomAccessDeniedHandler;
import org.qqsucc.booktify.common.security.exhandlers.CustomAuthenticationEntryPoint;
import org.qqsucc.booktify.common.security.filter.JwtAuthFilter;
import org.qqsucc.booktify.common.security.service.impl.CustomUserDetailsService;
import org.qqsucc.booktify.user.repository.entity.enums.UserRole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static lombok.AccessLevel.PRIVATE;
import static org.qqsucc.booktify.common.security.constants.SecurityConstants.PRIVATE_APIS;
import static org.qqsucc.booktify.common.security.constants.SecurityConstants.PUBLIC_APIS;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class SecurityConfig {

	CustomUserDetailsService userDetailsService;
	JwtAuthFilter jwtAuthFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.csrf(AbstractHttpConfigurer::disable)
				.cors(AbstractHttpConfigurer::disable)
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(auth -> auth
						.dispatcherTypeMatchers(DispatcherType.ASYNC).permitAll()
						.requestMatchers(HttpMethod.OPTIONS,"/**").permitAll()
						.requestMatchers(PUBLIC_APIS).permitAll()
						.requestMatchers(PRIVATE_APIS).hasAnyRole(
								UserRole.ROLE_CLIENT.getRole(), UserRole.ROLE_MASTER.getRole(), UserRole.ROLE_ADMIN.getRole()
						).anyRequest().anonymous()
				)
				.exceptionHandling(exception -> exception
						.authenticationEntryPoint(new CustomAuthenticationEntryPoint())
						.accessDeniedHandler(new CustomAccessDeniedHandler())
				)
				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider(passwordEncoder());
		provider.setUserDetailsService(userDetailsService);
		return provider;
	}

	@Bean
	public AuthenticationManager authenticationManager() {
		return new ProviderManager(authenticationProvider());
	}

}
