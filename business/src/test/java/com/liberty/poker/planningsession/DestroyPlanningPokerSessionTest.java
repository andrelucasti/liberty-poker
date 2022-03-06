package com.liberty.poker.planningsession;

import com.liberty.poker.linksession.DestroyLinkSessionFromPlanningSession;
import com.liberty.poker.member.RemoveMembersFromPlanningSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DestroyPlanningPokerSessionTest {

    private DestroyPlanningPokerSession subject;

    @Mock
    private PlanningSessionRepository planningSessionRepository;

    @Mock
    private DestroyLinkSessionFromPlanningSession destroyLinkSessionFromPlanningSession;
    
    @Mock
    private RemoveMembersFromPlanningSession removeMembersFromPlanningSession;

    @BeforeEach
    void setUp() {
        subject = new DestroyPlanningPokerSession(removeMembersFromPlanningSession, destroyLinkSessionFromPlanningSession, planningSessionRepository);
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
}