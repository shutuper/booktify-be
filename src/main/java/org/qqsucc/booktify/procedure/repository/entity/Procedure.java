package org.qqsucc.booktify.procedure.repository.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.qqsucc.booktify.common.entity.BaseEntity;
import org.qqsucc.booktify.procedure.repository.entity.enumeration.ProcedureStatus;
import org.qqsucc.booktify.user.repository.entity.User;

import java.time.Instant;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Table(name = "procedure")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = PRIVATE)
public class Procedure extends BaseEntity {

	String title;

	String description;

	@Enumerated(EnumType.STRING)
	ProcedureStatus status;

	@Min(5) // - 5 min
	@Max(720) // - 12 hours
	Integer duration; // in minutes

	Double price;

	@Column(name = "master_id")
	UUID masterId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "master_id", insertable = false, updatable = false)
	User master;

	@Builder.Default
	Instant createdDate = Instant.now();

	public boolean isNotActive() {
		return !ProcedureStatus.ACTIVE.equals(this.status);
	}

}
