package com.liberty.poker.member;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class RemoveMembersFromPlanningSession {

    private final MemberRepository memberRepository;

    public RemoveMembersFromPlanningSession(final MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void execute(final UUID planningSessionId) {
        memberRepository.deleteMemberBy(planningSessionId);
    }
}
