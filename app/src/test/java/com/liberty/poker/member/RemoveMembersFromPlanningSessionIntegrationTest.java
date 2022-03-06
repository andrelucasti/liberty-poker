package com.liberty.poker.member;

import com.liberty.poker.AbstractIntegrationTests;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class RemoveMembersFromPlanningSessionIntegrationTest extends AbstractIntegrationTests {

    @Autowired
    private RemoveMembersFromPlanningSession subject;

    @Test
    void shouldRemoveMembersFromThePlanningSession() {
        final var planningPokerSession = createPlanningSession();
        final var planningPokerSessionId = planningPokerSession.getId();

        memberRepository.save(new Member("Andre Lucas", planningPokerSessionId));
        memberRepository.save(new Member("Alexandre Lima", planningPokerSessionId));
        memberRepository.save(new Member("Vijay", planningPokerSessionId));

        subject.execute(planningPokerSessionId);

        final var memberList = memberRepository.findAll();

        Assertions.assertThat(memberList).isEmpty();
    }

    @Test
    void shouldRemoveMembersJustFromTheSamePlanningSession() {
        final var planningPokerSession1 = createPlanningSession();
        final var planningPokerSession2 = createPlanningSession();

        final var sessionId1 = planningPokerSession1.getId();
        final var sessionId2 = planningPokerSession2.getId();

        memberRepository.save(new Member("Andre Lucas", sessionId1));
        memberRepository.save(new Member("Alexandre Lima", sessionId1));

        final var vijay = new Member("Vijay", sessionId2);
        final var jason = new Member("Jason", sessionId2);
        memberRepository.save(vijay);
        memberRepository.save(jason);

        subject.execute(sessionId1);

        final var memberList = memberRepository.findAll();

        Assertions.assertThat(memberList).isNotEmpty();
        Assertions.assertThat(memberList).hasSize(2);
        Assertions.assertThat(memberList).contains(vijay, jason);
    }
}