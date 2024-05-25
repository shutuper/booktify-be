package org.qqsucc.booktify.salon.repository;

import org.qqsucc.booktify.salon.repository.entity.SalonMasterInvite;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SalonMasterInviteRepository extends JpaRepository<SalonMasterInvite, UUID> {

	@EntityGraph(attributePaths = "salon")
	Optional<SalonMasterInvite> findByToken(String inviteToken);
}
