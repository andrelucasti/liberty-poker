package com.liberty.poker.planningsession;

import com.liberty.poker.linksession.GenerateLinkSession;
import com.liberty.poker.linksession.LinkSession;
import org.springframework.stereotype.Service;

@Service
public class CreatePlanningPokerSession {
    private final PlanningSessionRepository planningSessionRepository;
    private final GenerateLinkSession generateLinkSession;

    public CreatePlanningPokerSession(final PlanningSessionRepository planningSessionRepository, 
                                      final GenerateLinkSession generateLinkSession) {
        this.planningSessionRepository = planningSessionRepository;
        this.generateLinkSession = generateLinkSession;
    }

    //TODO improve transactions "@Transaction"
    public PlanningPokerSessionDTO execute(final PlanningSession planningSession) {
        final var newPlanningSession = planningSessionRepository.save(planningSession);

        final var linkSession = new LinkSession(newPlanningSession.getId());
        final var newLinkSession = generateLinkSession.execute(linkSession);

        return new PlanningPokerSessionDTO(newPlanningSession, newLinkSession);
    }
}
