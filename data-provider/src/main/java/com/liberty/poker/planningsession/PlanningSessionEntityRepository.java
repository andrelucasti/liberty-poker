package com.liberty.poker.planningsession;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PlanningSessionEntityRepository extends JpaRepository<PlanningSessionEntity, UUID> {
}
