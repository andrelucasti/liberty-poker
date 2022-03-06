package com.liberty.poker.member;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class RemoveMembersFromPlanningSessionTest {

    private RemoveMembersFromPlanningSession subject;

    @Mock
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        subject = new RemoveMembersFromPlanningSession(memberRepository);
    }

    @Test
    void shouldRemoveMembersFromTheSamePlanningSession() {
        final var planningSessionId = UUID.randomUUID();
        subject.execute(planningSessionId);

        Mockito.verify(memberRepository).deleteMemberBy(planningSessionId);
    }
}