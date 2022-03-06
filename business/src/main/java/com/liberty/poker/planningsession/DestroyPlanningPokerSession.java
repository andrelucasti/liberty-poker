package com.liberty.poker.planningsession;

import com.liberty.poker.linksession.DestroyLinkSessionFromPlanningSession;
import com.liberty.poker.member.RemoveMembersFromPlanningSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class DestroyPlanningPokerSession {

    private final RemoveMembersFromPlanningSession removeMembersFromPlanningSession;
    private final DestroyLinkSessionFromPlanningSession destroyLinkSessionFromPlanningSession;
    private final PlanningSessionRepository planningSessionRepository;

    public DestroyPlanningPokerSession(final RemoveMembersFromPlanningSession removeMembersFromPlanningSession,
                                       final DestroyLinkSessionFromPlanningSession destroyLinkSessionFromPlanningSession,
                                       final PlanningSessionRepository planningSessionRepository) {

        this.removeMembersFromPlanningSession = removeMembersFromPlanningSession;
        this.destroyLinkSessionFromPlanningSession = destroyLinkSessionFromPlanningSession;
        this.planningSessionRepository = planningSessionRepository;
    }

    @Transactional
    public void execute(final UUID planningSessionId) {
        removeMembersFromPlanningSession.execute(planningSessionId);
        destroyLinkSessionFromPlanningSession.execute(planningSessionId);
        planningSessionRepository.deleteById(planningSessionId);
    }
}
