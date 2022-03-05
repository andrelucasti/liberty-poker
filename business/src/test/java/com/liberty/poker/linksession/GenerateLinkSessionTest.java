package com.liberty.poker.linksession;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)

class GenerateLinkSessionTest {

    private GenerateLinkSession subject;

    @Mock
    private LinkSessionRepository linkSessionRepository;

    @BeforeEach
    void setUp() {
        subject = new GenerateLinkSession(linkSessionRepository);
    }

    @Test
    void shouldSaveLinkSession() {
        final var linkSession = new LinkSession(UUID.randomUUID());

        subject.execute(linkSession);

        verify(linkSessionRepository).save(eq(linkSession));
    }

    @Test
    void shouldGenerateLinkWhenSaveLinkSession() {
        final var linkSessionArgumentCaptor = ArgumentCaptor.forClass(LinkSession.class);
        final var planningSessionId = UUID.randomUUID();
        final var linkSession = new LinkSession(planningSessionId);

        subject.execute(linkSession);

        verify(linkSessionRepository).save(linkSessionArgumentCaptor.capture());
        final var currentLinkSession = linkSessionArgumentCaptor.getValue();
        Assertions.assertThat(currentLinkSession.getLink()).isEqualTo("/room/".concat(planningSessionId.toString()));
    }
}