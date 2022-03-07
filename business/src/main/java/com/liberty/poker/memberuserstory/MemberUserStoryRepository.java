package com.liberty.poker.memberuserstory;

import com.google.common.annotations.VisibleForTesting;
import com.liberty.poker.planningsession.PlanningSession;

import java.util.List;
import java.util.UUID;

public interface MemberUserStoryRepository {
    MemberUserStory save(MemberUserStory memberUserStory);

    List<MemberUserStory> findAll();

    List<MemberUserStory> findByMemberIdAndPlanningSessionId(UUID id, UUID planningSessionId);
    @VisibleForTesting
    void deleteAll();
}
