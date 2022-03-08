package com.liberty.poker.memberuserstory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface MemberUserStoryRepositoryEntity extends JpaRepository<MemberUserStoryEntity, UUID> {
    List<MemberUserStoryEntity> findByMemberIdAndPlanningSessionId(UUID memberId, UUID planningSessionId);
    List<MemberUserStoryEntity> findByUserStoryIdAndPlanningSessionId(UUID memberId, UUID planningSessionId);

    @Modifying
    @Query(value = "update MEMBER_USER_STORY set VALUE_VOTE = :voteValue where MEMBER_ID = :memberId and USER_STORY_ID = :userStoryId and PLANNING_SESSION_ID = :planningSessionId", nativeQuery = true)
    void updateMemberUserStoryBy(@Param("memberId") UUID memberId,
                                 @Param("userStoryId") UUID userStoryId,
                                 @Param("planningSessionId") UUID planningSessionId,
                                 @Param("voteValue")long voteValue);
}
