package com.liberty.poker.planningsession;

import com.liberty.poker.linksession.DestroyLinkSessionFromPlanningSession;
import com.liberty.poker.member.RemoveMembersFromPlanningSession;
import com.liberty.poker.memberuserstory.MemberUserStoryRepository;
import com.liberty.poker.userstory.RemoveUserStoryFromPlanningSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class DestroyPlanningSession {

    private final MemberUserStoryRepository memberUserStoryRepository;
    private final RemoveUserStoryFromPlanningSession removeUserStoryFromPlanningSession;
    private final RemoveMembersFromPlanningSession removeMembersFromPlanningSession;
    private final DestroyLinkSessionFromPlanningSession destroyLinkSessionFromPlanningSession;
    private final PlanningSessionRepository planningSessionRepository;

    public DestroyPlanningSession(final MemberUserStoryRepository memberUserStoryRepository,
                                  final RemoveUserStoryFromPlanningSession removeUserStoryFromPlanningSession,
                                  final RemoveMembersFromPlanningSession removeMembersFromPlanningSession,
                                  final DestroyLinkSessionFromPlanningSession destroyLinkSessionFromPlanningSession,
                                  final PlanningSessionRepository planningSessionRepository) {
        this.memberUserStoryRepository = memberUserStoryRepository;
        this.removeUserStoryFromPlanningSession = removeUserStoryFromPlanningSession;
        this.removeMembersFromPlanningSession = removeMembersFromPlanningSession;
        this.destroyLinkSessionFromPlanningSession = destroyLinkSessionFromPlanningSession;
        this.planningSessionRepository = planningSessionRepository;
    }

    @Transactional
    public void execute(final UUID planningSessionId) {

        memberUserStoryRepository.deleteBy(planningSessionId);
        removeUserStoryFromPlanningSession.execute(planningSessionId);
        removeMembersFromPlanningSession.execute(planningSessionId);
        destroyLinkSessionFromPlanningSession.execute(planningSessionId);
        planningSessionRepository.deleteById(planningSessionId);
    }
}
