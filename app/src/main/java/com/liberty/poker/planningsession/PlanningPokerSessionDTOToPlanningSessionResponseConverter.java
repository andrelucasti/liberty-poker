package com.liberty.poker.planningsession;

import org.springframework.stereotype.Component;

@Component
public class PlanningPokerSessionDTOToPlanningSessionResponseConverter {

    public PlanningSessionResponse converter(final PlanningPokerSessionDTO planningPokerSessionDTO){
        final var newPlanningSession = planningPokerSessionDTO.getNewPlanningSession();
        final var newLinkSession = planningPokerSessionDTO.getNewLinkSession();

        return new PlanningSessionResponse(newPlanningSession.getId(),
                newPlanningSession.getTitle(),
                newPlanningSession.getDeckType().toString(),
                newLinkSession.getLink());
    }
}
