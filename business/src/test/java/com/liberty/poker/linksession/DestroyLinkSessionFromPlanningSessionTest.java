package com.liberty.poker.linksession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class DestroyLinkSessionFromPlanningSessionTest {

    private DestroyLinkSessionFromPlanningSession subject;

    @Mock
    private LinkSessionRepository linkSessionRepository;

    @BeforeEach
    void setUp() {
        subject = new DestroyLinkSessionFromPlanningSession(linkSessionRepository);
    }

    @Test
    void shouldRemoveLinkSession() {
        final var planningSessionId = UUID.randomUUID();

        subject.execute(planningSessionId);

        Mockito.verify(linkSessionRepository).deleteBy(Mockito.eq(planningSessionId));
    }
}