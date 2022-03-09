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

    void deleteByPlanningSessionId(UUID planningSessionId);

    @Modifying
    @Query(value = "update MEMBER_USER_STORY m set m.version=m.version+1, m.voteValue = :voteValue where m.memberId = :memberId and m.userStoryId = :userStoryId and m.planningSessionId = :planningSessionId")
    void updateMemberUserStoryBy(@Param("memberId") UUID memberId,
                                 @Param("userStoryId") UUID userStoryId,
                                 @Param("planningSessionId") UUID planningSessionId,
                                 @Param("voteValue")long voteValue);
}
