package org.qqsucc.booktify.user.repository.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.qqsucc.booktify.common.entity.BaseEntity;
import org.qqsucc.booktify.user.repository.entity.enums.UserRole;
import org.qqsucc.booktify.user.repository.entity.enums.UserStatus;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Table(name = "base_user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = PRIVATE)
public class User extends BaseEntity {

	@Enumerated(EnumType.STRING)
	UserRole role;

	@Enumerated(EnumType.STRING)
	UserStatus status;

	String firstname;

	String lastname;

	@Email(message = "Invalid user email")
	String email; //nullable

	@Builder.Default
	Boolean isEmailVerified = false;

	@Pattern(regexp = "380\\d{9}", message = "Invalid user phone number")
	String phone;

	String password;

}
