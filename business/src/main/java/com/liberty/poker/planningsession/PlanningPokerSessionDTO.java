package com.liberty.poker.planningsession;

import com.liberty.poker.linksession.LinkSession;
import lombok.Getter;

@Getter
public class PlanningPokerSessionDTO {
    private final PlanningSession newPlanningSession;
    private final LinkSession newLinkSession;

    public PlanningPokerSessionDTO(final PlanningSession newPlanningSession,
                                   final LinkSession newLinkSession) {
        this.newPlanningSession = newPlanningSession;
        this.newLinkSession = newLinkSession;
    }
}
