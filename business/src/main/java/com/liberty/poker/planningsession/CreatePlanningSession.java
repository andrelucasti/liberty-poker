package com.liberty.poker.planningsession;

import com.liberty.poker.linksession.GenerateLinkSession;
import com.liberty.poker.linksession.LinkSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreatePlanningSession {
    private final PlanningSessionRepository planningSessionRepository;
    private final GenerateLinkSession generateLinkSession;

    public CreatePlanningSession(final PlanningSessionRepository planningSessionRepository,
                                 final GenerateLinkSession generateLinkSession) {
        this.planningSessionRepository = planningSessionRepository;
        this.generateLinkSession = generateLinkSession;
    }

    @Transactional
    public PlanningSessionDTO execute(final PlanningSession planningSession) {
        final var newPlanningSession = planningSessionRepository.save(planningSession);

        final var linkSession = new LinkSession(newPlanningSession.getId());
        final var newLinkSession = generateLinkSession.execute(linkSession);

        return new PlanningSessionDTO(newPlanningSession, newLinkSession);
    }
}
