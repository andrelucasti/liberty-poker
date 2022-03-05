package com.liberty.poker.planningsession;

import org.springframework.stereotype.Component;

@Component
public class PlanningSessionEntityToPlanningSessionConverter {

    public PlanningSession converter(final PlanningSessionEntity planningSessionEntity){
        return new PlanningSession(planningSessionEntity.getUuid(), planningSessionEntity.getTitle(), planningSessionEntity.getDeckType());
    }
}
