package org.qqsucc.booktify.salon.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;
import org.jetbrains.annotations.Nullable;
import org.qqsucc.booktify.common.entity.BaseEntity;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Table(name = "salon")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = PRIVATE)
public class Salon extends BaseEntity {

	String title;

	@Length(min = 3, max = 30)
	@Pattern(regexp = "^\\w+$", message = "Only 'a-z', 'A-Z', '0-9' and '_' characters allowed in link name")
	String linkName;

	@Nullable
	String description;

	String address;

	@Nullable
	UUID avatarId;

	@Nullable
	UUID bannerId;

	@Builder.Default
	Instant createdDate = Instant.now();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "salon")
	Set<SalonMaster> salonMasters;

}
