package org.qqsucc.booktify.workschedule.repository.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.qqsucc.booktify.common.entity.BaseEntity;
import org.qqsucc.booktify.user.repository.entity.User;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Table(name = "master_work_schedule")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = PRIVATE)
public class MasterWorkSchedule extends BaseEntity {

	@Enumerated(EnumType.STRING)
	DayOfWeek dayOfWeek;

	LocalTime startTime;

	LocalTime endTime;

	@Column(name = "master_id")
	UUID masterId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "master_id", insertable = false, updatable = false)
	User master;

	@NotNull
	public boolean isWorkingDay(@NotNull LocalDate date) {
		return Objects.equals(this.dayOfWeek, date.getDayOfWeek());
	}

}
