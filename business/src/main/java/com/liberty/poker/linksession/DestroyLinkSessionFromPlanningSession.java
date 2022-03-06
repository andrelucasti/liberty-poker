package com.liberty.poker.linksession;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DestroyLinkSessionFromPlanningSession {

    private final LinkSessionRepository linkSessionRepository;

    public DestroyLinkSessionFromPlanningSession(final LinkSessionRepository linkSessionRepository) {
        this.linkSessionRepository = linkSessionRepository;
    }

    public void execute(final UUID planningSessionId) {
        linkSessionRepository.deleteBy(planningSessionId);
    }
}
