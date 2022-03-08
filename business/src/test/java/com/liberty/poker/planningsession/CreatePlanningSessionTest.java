package com.liberty.poker.planningsession;

import com.liberty.poker.linksession.GenerateLinkSession;
import com.liberty.poker.linksession.LinkSession;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static com.liberty.poker.planningsession.PlanningSession.DeckType.FIBONACCI;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreatePlanningSessionTest {

    private CreatePlanningSession subject;

    @Mock
    private PlanningSessionRepository planningSessionRepository;

    @Mock
    private GenerateLinkSession generateLinkSession;

    @BeforeEach
    void setUp() {
        subject = new CreatePlanningSession(planningSessionRepository, generateLinkSession);
    }

    @Test
    void shouldCreatePlanningPokerSession() {
        final var planningSession = new PlanningSession(UUID.randomUUID(), "Liberty Planning Session", FIBONACCI);
        when(planningSessionRepository.save(eq(planningSession)))
                .thenReturn(planningSession);

        subject.execute(planningSession);

        verify(planningSessionRepository).save(eq(planningSession));
    }

    @Test
    void shouldCreateLinkSessionWhenCreateANewPlanningSession() {
        final var linkSessionArgumentCaptor = ArgumentCaptor.forClass(LinkSession.class);
        final var planningSession = new PlanningSession(UUID.randomUUID(), "Liberty Planning Session", FIBONACCI);
        when(planningSessionRepository.save(eq(planningSession)))
                .thenReturn(planningSession);

        subject.execute(planningSession);

        verify(generateLinkSession).execute(linkSessionArgumentCaptor.capture());

        final var linkSession = linkSessionArgumentCaptor.getValue();
        Assertions.assertThat(linkSession.getPlanningSessionId()).isEqualTo(planningSession.getId());
    }
}