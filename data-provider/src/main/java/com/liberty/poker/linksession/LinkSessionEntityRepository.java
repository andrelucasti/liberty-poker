package com.liberty.poker.linksession;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface LinkSessionEntityRepository extends JpaRepository<LinkSessionEntity, UUID> {
    void deleteByPlanningSessionId(UUID planningSessionId);
    Optional<LinkSessionEntity> findByPlanningSessionId(UUID planningSessionId);
}
