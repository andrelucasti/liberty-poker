package com.liberty.poker.planningsession;

import com.liberty.poker.linksession.LinkSession;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class PlanningSessionDTO {
    private final PlanningSession newPlanningSession;
    private final LinkSession newLinkSession;

    public PlanningSessionDTO(final PlanningSession newPlanningSession,
                              final LinkSession newLinkSession) {
        this.newPlanningSession = newPlanningSession;
        this.newLinkSession = newLinkSession;
    }
}
