package com.liberty.poker.member;

import com.liberty.poker.AbstractIntegrationTests;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class CreateMemberIntegrationTest extends AbstractIntegrationTests {

    @Autowired
    private CreateMember subject;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void shouldSaveMember() {
        final var planningPokerSession = createPlanningPokerSession();
        final var memberExpected = new Member("Andre Lucas", planningPokerSession.getId());

        subject.execute(memberExpected);

        final var actualMember = memberRepository.findAll().stream().findFirst().get();

        Assertions.assertThat(actualMember).isEqualTo(memberExpected);
    }
}