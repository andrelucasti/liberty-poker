package com.liberty.poker.memberuserstory;

import com.google.common.annotations.VisibleForTesting;

import java.util.List;
import java.util.UUID;

public interface MemberUserStoryRepository {
    MemberUserStory save(MemberUserStory memberUserStory);

    List<MemberUserStory> findAll();

    List<MemberUserStory> findByMemberIdAndPlanningSessionId(UUID memberId, UUID planningSessionId);

    List<MemberUserStory> findByUserStoryIdAndPlanningSessionId(UUID userStoryId, UUID planningSessionId);

    void updateVoteFrom(MemberUserStory memberUserStory);

    @VisibleForTesting
    void deleteAll();
}
