package com.liberty.poker.member;

import com.google.common.annotations.VisibleForTesting;

import java.util.List;
import java.util.UUID;

public interface MemberRepository {
    Member save(Member member);
    List<Member> findAll();
    List<Member> findMembersBy(UUID planningSessionId);
    void deleteMemberBy(UUID planningSessionId);

    @VisibleForTesting
    void deleteAll();
}
