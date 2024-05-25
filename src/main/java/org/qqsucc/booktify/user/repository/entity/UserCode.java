package org.qqsucc.booktify.user.repository.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.qqsucc.booktify.common.entity.BaseEntity;

import java.time.Instant;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Table(name = "user_code")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = PRIVATE)
public class UserCode extends BaseEntity {

	@Column(name = "user_id")
	UUID userId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", insertable = false, updatable = false)
	User user;

	String code;

	Instant expirationDate;

	@Builder.Default
	Instant createdDate = Instant.now();
}
