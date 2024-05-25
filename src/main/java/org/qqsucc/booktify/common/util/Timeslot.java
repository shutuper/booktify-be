package org.qqsucc.booktify.common.util;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public interface Timeslot extends Comparable<Timeslot> {

	DateTimeFormatter DATE_FORMATTER = DateTimeFormatter
			.ofLocalizedDate(FormatStyle.MEDIUM)
			.withLocale(Locale.ENGLISH)
			.withZone(ZoneId.of("America/New_York"));
	DateTimeFormatter TIME_FORMATTER = DateTimeFormatter
			.ofPattern("h:mm a")
			.withZone(ZoneId.of("America/New_York"));

	Instant getStartDate();

	Instant getEndDate();

	@Override // accepts that startDate always <= endDate
	default int compareTo(@NotNull Timeslot to) {
		return this.getStartDate().compareTo(to.getStartDate());
	}

	/**
	 * @return whether current timeslot is pending
	 */
	@JsonIgnore
	default boolean isPending() {
		return Instant.now().compareTo(this.getStartDate()) <= 0;
	}

	/**
	 * @return timeslot duration in minutes
	 */
	@JsonIgnore
	default Long getDuration() { // endDate is exclusive in Duration.between() so -> plusNanos(1L)
		return Duration.between(this.getStartDate(), this.getEndDate().plusNanos(1L)).getSeconds() / 60;
	}

	@JsonIgnore
	default String toDateString() {
		return DATE_FORMATTER.format(getStartDate());
	}

	@JsonIgnore
	default String toTimeString() {
		return String.format(
				"%s - %s",
				TIME_FORMATTER.format(getStartDate()),
				TIME_FORMATTER.format(getEndDate())
		).toLowerCase() + " EST";
	}

}
