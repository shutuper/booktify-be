package org.qqsucc.booktify.user.repository.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.Nullable;
import org.qqsucc.booktify.common.entity.BaseEntity;
import org.qqsucc.booktify.salon.repository.entity.SalonMaster;
import org.qqsucc.booktify.user.repository.entity.enums.UserRole;
import org.qqsucc.booktify.user.repository.entity.enums.UserStatus;

import java.time.Instant;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Table(name = "base_user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = PRIVATE)
public class User extends BaseEntity {

	@Enumerated(EnumType.STRING)
	UserRole role;

	@Enumerated(EnumType.STRING)
	UserStatus status;

	String firstname;

	String lastname;

	@Nullable
	@Email(message = "Invalid user email")
	String email;

	@Nullable
	UUID avatarId;

	@Builder.Default
	Boolean isEmailVerified = false;

	@Pattern(regexp = "380\\d{9}", message = "Invalid user phone number")
	String phone;

	String password;

	@EqualsAndHashCode.Exclude
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "master")
	SalonMaster salonMaster;

	@Builder.Default
	Instant createdDate = Instant.now();

	public boolean isNotActive() {
		return UserStatus.NOT_ACTIVE.equals(this.status);
	}

	public boolean isMaster() {
		return UserRole.ROLE_MASTER.equals(this.role);
	}

	public boolean isClient() {
		return UserRole.ROLE_CLIENT.equals(this.role);
	}
}
