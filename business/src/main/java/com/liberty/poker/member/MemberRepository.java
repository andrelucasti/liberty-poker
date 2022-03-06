package com.liberty.poker.member;

import java.util.List;
import java.util.UUID;

public interface MemberRepository {
    Member save(Member member);
    List<Member> findAll();
    List<Member> findMembersBy(UUID planningSessionId);
}
