package com.liberty.poker.member;

import com.liberty.poker.planningsession.PlanningSession;
import com.liberty.poker.planningsession.PlanningSessionNotFoundException;
import com.liberty.poker.planningsession.PlanningSessionRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.liberty.poker.planningsession.PlanningSession.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InviteMemberToPlanningSessionTest {
    private InviteMemberToPlanningSession subject;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PlanningSessionRepository planningSessionRepository;

    @BeforeEach
    void setUp() {
        subject = new InviteMemberToPlanningSession(new CreateMember(memberRepository), memberRepository, planningSessionRepository);
    }

    @Test
    void shouldReturnTheTitleOfPlanningPokerSessionWhenEnterInAnyPlanningPokerSession() throws PlanningSessionNotFoundException {
        final var planningSessionId = UUID.randomUUID();
        final var member = new Member("Andre Lucas", planningSessionId);
        final var titlePlanningPokerSessionExpected = "Liberty P Session";

        when(planningSessionRepository.findById(eq(planningSessionId)))
                .thenReturn(Optional.of(new PlanningSession(planningSessionId, titlePlanningPokerSessionExpected, DeckType.FIBONACCI)));

        final var actualTitlePlanningPokerSession = subject.execute(member);

        Assertions.assertThat(actualTitlePlanningPokerSession.getTitle()).isEqualTo(titlePlanningPokerSessionExpected);
    }

    @Test
    void shouldReturnAllMembersInPlanningPokerSessionWhenEnterInAnyPlanningPokerSession() throws PlanningSessionNotFoundException {
        final var planningSessionId = UUID.randomUUID();
        final var newMember = new Member("Andre Lucas", planningSessionId);
        final var member2 = new Member("Alexandre Lima", planningSessionId);
        final var member3 = new Member("Vijay", planningSessionId);

        when(planningSessionRepository.findById(eq(planningSessionId)))
                .thenReturn(Optional.of(new PlanningSession(planningSessionId, "Liberty P Session", DeckType.FIBONACCI)));

        when(memberRepository.findByPlanningSessionId(eq(planningSessionId)))
                .thenReturn(List.of(newMember, member2, member3));

        final var planningPokerRoomSessionDTO = subject.execute(newMember);

        Assertions.assertThat(planningPokerRoomSessionDTO.getMembers()).isNotEmpty();
        Assertions.assertThat(planningPokerRoomSessionDTO.getMembers()).contains(newMember, member2, member3);
    }

    @Test
    void shouldReturnAllUserStoriesInPlanningPokerRoomSessionWhenEnterAnyPlanningSession() {
        //TODO ...
    }
}