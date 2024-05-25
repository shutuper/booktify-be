package org.qqsucc.booktify.salon.service;

import org.qqsucc.booktify.salon.repository.entity.Salon;

import java.util.UUID;

public interface SalonService {

	Salon save(Salon salon);

	boolean existsByLinkName(String linkName);

	Salon findByMasterIdAndId(UUID masterId, UUID salonId);

	Salon findByMasterId(UUID masterId);

	Salon findByLinkName(String linkName);
}
