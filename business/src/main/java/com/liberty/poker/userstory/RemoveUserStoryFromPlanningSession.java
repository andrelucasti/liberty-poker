package com.liberty.poker.userstory;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RemoveUserStoryFromPlanningSession {
    private final UserStoryRepository userStoryRepository;

    public RemoveUserStoryFromPlanningSession(final UserStoryRepository userStoryRepository) {
        this.userStoryRepository = userStoryRepository;
    }

    public void execute(final UUID planningSessionId) {
        userStoryRepository.deleteByPlanningSessionId(planningSessionId);
    }
}
