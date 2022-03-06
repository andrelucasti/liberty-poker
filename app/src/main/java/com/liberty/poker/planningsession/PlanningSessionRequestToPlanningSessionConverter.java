package com.liberty.poker.planningsession;

import org.springframework.stereotype.Component;

import static com.liberty.poker.planningsession.PlanningSession.*;

@Component
public class PlanningSessionRequestToPlanningSessionConverter {

    public PlanningSession converter(final PlanningSessionRequest planningSessionRequest){
        return new PlanningSession(planningSessionRequest.getTitle(), DeckType.valueOf(planningSessionRequest.getDeckType()));
    }
}
