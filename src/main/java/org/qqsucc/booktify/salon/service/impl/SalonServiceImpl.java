package org.qqsucc.booktify.salon.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.qqsucc.booktify.common.exception.NotFoundException;
import org.qqsucc.booktify.salon.repository.SalonRepository;
import org.qqsucc.booktify.salon.repository.entity.Salon;
import org.qqsucc.booktify.salon.service.SalonService;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class SalonServiceImpl implements SalonService {

	SalonRepository salonRepository;

	@Override
	public Salon save(Salon salon) {
		return salonRepository.save(salon);
	}

	@Override
	public boolean existsByLinkName(String linkName) {
		return salonRepository.existsByLinkName(linkName);
	}

	@Override
	public Salon findByMasterIdAndId(UUID masterId, UUID salonId) {
		return salonRepository
				.findByIdAndSalonMasters_MasterId(salonId, masterId)
				.orElseThrow(() -> new NotFoundException("Salon not found"));
	}

	@Override
	public Salon findByMasterId(UUID masterId) {
		return salonRepository
				.findBySalonMasters_MasterId(masterId)
				.orElseThrow(() -> new NotFoundException("Master's salon not found"));
	}

	@Override
	public Salon findByLinkName(String linkName) {
		return salonRepository
				.findByLinkName(linkName)
				.orElseThrow(() -> new NotFoundException("Salon not found by link name"));
	}
}
