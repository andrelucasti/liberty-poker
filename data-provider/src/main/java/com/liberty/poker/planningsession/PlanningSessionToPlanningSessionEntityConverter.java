package com.liberty.poker.planningsession;

import org.springframework.stereotype.Component;

@Component
public class PlanningSessionToPlanningSessionEntityConverter {

    public PlanningSessionEntity converter(final PlanningSession planningSession){
        return PlanningSessionEntity.of(planningSession.getTitle(), planningSession.getDeckType());
    }
}
