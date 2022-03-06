package com.liberty.poker.member;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MemberRepositoryEntity extends JpaRepository<MemberEntity, UUID> {
    List<Member> findMembersByPlanningSessionId(UUID planningSessionId);

}
