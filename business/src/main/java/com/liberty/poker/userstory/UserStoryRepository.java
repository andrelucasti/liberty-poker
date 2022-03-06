package com.liberty.poker.userstory;

import com.google.common.annotations.VisibleForTesting;

import java.util.List;
import java.util.UUID;

public interface UserStoryRepository {
    UserStory save(UserStory userStory);
    List<UserStory> findAll();
    void deleteById(UUID id);
    void deleteByPlanningSessionId(UUID planningSessionId);

    @VisibleForTesting
    void deleteAll();
}
