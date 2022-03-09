package com.liberty.poker.planningsession;

import com.liberty.poker.linksession.DestroyLinkSessionFromPlanningSession;
import com.liberty.poker.member.RemoveMembersFromPlanningSession;
import com.liberty.poker.memberuserstory.MemberUserStoryRepository;
import com.liberty.poker.userstory.RemoveUserStoryFromPlanningSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DestroyPlanningSessionTest {

    private DestroyPlanningSession subject;

    @Mock
    private PlanningSessionRepository planningSessionRepository;

    @Mock
    private DestroyLinkSessionFromPlanningSession destroyLinkSessionFromPlanningSession;
    
    @Mock
    private RemoveMembersFromPlanningSession removeMembersFromPlanningSession;

    @Mock
    private RemoveUserStoryFromPlanningSession removeUserStoryFromPlanningSession;

    @Mock
    private MemberUserStoryRepository memberUserStoryRepository;

    @BeforeEach
    void setUp() {
        subject = new DestroyPlanningSession(memberUserStoryRepository, removeUserStoryFromPlanningSession, removeMembersFromPlanningSession, destroyLinkSessionFromPlanningSession, planningSessionRepository);
    }

    @Test
    void shouldDestroyPlanningPokerSession() {
        final var planningSessionId = UUID.randomUUID();

        subject.execute(planningSessionId);

        verify(planningSessionRepository).deleteById(eq(planningSessionId));
    }

    @Test
    void shouldRemoveAllMembersFromPlanningSessionWhenDestroyPlanningSession() {
        final var planningSessionId = UUID.randomUUID();

        subject.execute(planningSessionId);

        verify(removeMembersFromPlanningSession).execute(eq(planningSessionId));
        verify(planningSessionRepository).deleteById(eq(planningSessionId));
    }

    @Test
    void shouldRemoveTheLinkFromPlanningSessionWhenDestroyPlanningSession() {
        final var planningSessionId = UUID.randomUUID();

        subject.execute(planningSessionId);

        verify(destroyLinkSessionFromPlanningSession).execute(eq(planningSessionId));
        verify(planningSessionRepository).deleteById(eq(planningSessionId));
    }

    @Test
    void shouldRemoveTheUserStoryFromPlanningSessionWhenDestroyPlanningSession() {
        final var planningSessionId = UUID.randomUUID();

        subject.execute(planningSessionId);

        verify(removeUserStoryFromPlanningSession).execute(eq(planningSessionId));
        verify(planningSessionRepository).deleteById(eq(planningSessionId));
    }

    @Test
    void shouldRemoveTheMemberUserStoryFromPlanningSessionWhenDestroyPlanningSession() {
        final var planningSessionId = UUID.randomUUID();

        subject.execute(planningSessionId);

        verify(memberUserStoryRepository).deleteBy(eq(planningSessionId));
        verify(planningSessionRepository).deleteById(eq(planningSessionId));
    }
}