package com.liberty.poker.planningsession;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PlanningSessionRepository {
    PlanningSession save(PlanningSession planningSession);
    Optional<PlanningSession> findById(UUID id);
    PlanningSession findMandatoryById(UUID id);

    List<PlanningSession> findAll();
}
