package org.qqsucc.booktify.salon.repository.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.qqsucc.booktify.common.entity.BaseEntity;
import org.qqsucc.booktify.user.repository.entity.User;

import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Table(name = "salon_master")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = PRIVATE)
public class SalonMaster extends BaseEntity {

	@Column(name = "master_id")
	UUID masterId;

	@EqualsAndHashCode.Exclude
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "master_id", insertable = false, updatable = false)
	User master;

	@Column(name = "salon_id")
	UUID salonId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "salon_id", insertable = false, updatable = false)
	Salon salon;

}
