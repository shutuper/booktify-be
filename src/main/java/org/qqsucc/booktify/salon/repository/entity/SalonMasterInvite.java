package org.qqsucc.booktify.salon.repository.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.qqsucc.booktify.common.entity.BaseEntity;

import java.time.Instant;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Table(name = "salon_master_invite")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = PRIVATE)
public class SalonMasterInvite extends BaseEntity {

	String token;

	Instant expirationDate;

	@Column(name = "salon_id")
	UUID salonId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "salon_id", insertable = false, updatable = false)
	Salon salon;

	@Builder.Default
	Instant createdDate = Instant.now();

}
