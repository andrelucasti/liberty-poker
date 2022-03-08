package com.liberty.poker.userstory;

import com.google.common.annotations.VisibleForTesting;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserStoryRepository {
    UserStory save(UserStory userStory);
    List<UserStory> findAll();
    void deleteById(UUID id);
    void deleteByPlanningSessionId(UUID planningSessionId);

    List<UserStory> findByPlanningSessionId(UUID planningSessionId);

    @VisibleForTesting
    void deleteAll();

    void updateStatus(UserStory userStory);


    Optional<UserStory> findById(UUID id);
    UserStory findMandatoryById(UUID id);
}
