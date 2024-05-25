package org.qqsucc.booktify.common.dto;

import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.ObjectUtils;
import org.qqsucc.booktify.common.exception.BusinessException;
import org.qqsucc.booktify.common.util.Timeslot;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@FieldDefaults(level = PRIVATE)
@SuperBuilder(toBuilder = true)
public class TimeslotDto implements Timeslot {

	@NotNull(message = "Missing required parameter: 'startDate'")
	@EqualsAndHashCode.Include
	Instant startDate;

	@NotNull(message = "Missing required parameter: 'endDate'")
	@EqualsAndHashCode.Include
	Instant endDate;

	/**
	 * @throws BusinessException if any of arguments is null or startDate > endDate
	 */
	public TimeslotDto(Instant startDate, Instant endDate) throws BusinessException {
		if (ObjectUtils.anyNull(startDate, endDate) || startDate.isAfter(endDate)) {
			throw new BusinessException("Illegal arguments passed to TimeslotDto constructor");
		}
		this.startDate = startDate;
		this.endDate = endDate;
	}

	/**
	 * @return intersection of timeslots or null if they don't intersect
	 */
	public TimeslotDto getIntersection(@NotNull TimeslotDto timeslot) {
		Instant newStartDate = startDate.isAfter(timeslot.startDate) ?
				startDate : timeslot.startDate;

		Instant newEndDate = endDate.isBefore(timeslot.endDate) ?
				endDate : timeslot.endDate;

		return newStartDate.isBefore(newEndDate) ?
				new TimeslotDto(newStartDate, newEndDate) : null;
	}

	/**
	 * @param busyTimeslots timeslot to be cut from this
	 * @return list of timeslots between this startDate and endDate that don't intersect with busyTimeslots
	 */
	public List<TimeslotDto> subtract(Collection<TimeslotDto> busyTimeslots) {
		List<TimeslotDto> result = new ArrayList<>();
		List<TimeslotDto> overlaps = busyTimeslots
				.stream()
				.filter(this::isOverlapped)
				.collect(Collectors.toList());

		if (overlaps.isEmpty()) {
			result.add(this);
		} else {
			TimeslotDto overlapped = overlaps.remove(0);
			boolean startsBeforeOverlapped = startDate.compareTo(overlapped.startDate) < 0;
			boolean endsAfterOverlapped = endDate.compareTo(overlapped.endDate) > 0;

			if (startsBeforeOverlapped && endsAfterOverlapped) {
				result.addAll(new TimeslotDto(startDate, overlapped.startDate).subtract(overlaps));
				result.addAll(new TimeslotDto(overlapped.endDate, endDate).subtract(overlaps));

			} else if (startsBeforeOverlapped) {
				result.addAll(new TimeslotDto(startDate, overlapped.startDate).subtract(overlaps));

			} else if (endsAfterOverlapped) {
				result.addAll(new TimeslotDto(overlapped.endDate, endDate).subtract(overlaps));

			}
		}

		return result;
	}

	/**
	 * @return list of new available timeslots that don't intersect busy
	 */
	public static Set<TimeslotDto> subtract(Collection<TimeslotDto> available, Collection<TimeslotDto> busy) {
		for (TimeslotDto occupied : busy) {
			available = subtract(available, occupied);
		}

		return new HashSet<>(available);
	}

	private static List<TimeslotDto> subtract(Collection<TimeslotDto> available, TimeslotDto busy) {
		List<TimeslotDto> subtractionResults = new ArrayList<>();
		available.forEach(timeSlot -> subtractionResults.addAll(timeSlot.subtract(List.of(busy))));
		return subtractionResults;
	}

	/**
	 * @return whether this timeslot overlaps other
	 */
	public boolean isOverlapped(TimeslotDto other) {
		Instant newStartDate = startDate.isAfter(other.startDate) ?
				startDate : other.startDate;

		Instant newEndDate = endDate.isBefore(other.endDate) ?
				endDate : other.endDate;

		return newStartDate.isBefore(newEndDate);
	}

	/**
	 * @param duration in minutes
	 * @return list of timeslots between this startDate and endDate with specified duration
	 * @throws BusinessException if duration less or equal to 0
	 */
	public List<TimeslotDto> splitByDuration(Long duration) {
		if (duration <= 0) {
			throw new BusinessException("Timeslot duration should be greater than 0");
		}

		if (startDate.compareTo(endDate) >= 0) {
			return List.of();
		}

		List<TimeslotDto> splitTimeslots = new ArrayList<>();

		Instant newStartDate = startDate;
		Instant newEndDate = newStartDate.plus(duration, ChronoUnit.MINUTES);

		while (newEndDate.compareTo(endDate) <= 0) {
			splitTimeslots.add(new TimeslotDto(newStartDate, newEndDate));

			newStartDate = newStartDate.plus(duration, ChronoUnit.MINUTES);
			newEndDate = newEndDate.plus(duration, ChronoUnit.MINUTES);
		}

		return splitTimeslots;
	}

}
