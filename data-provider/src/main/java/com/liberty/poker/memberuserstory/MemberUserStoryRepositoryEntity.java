package com.liberty.poker.memberuserstory;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MemberUserStoryRepositoryEntity extends JpaRepository<MemberUserStoryEntity, UUID> {
    List<MemberUserStoryEntity> findByMemberIdAndPlanningSessionId(UUID id, UUID planningSessionId);
}
