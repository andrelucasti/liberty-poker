package com.liberty.poker.linksession;

import com.liberty.poker.AbstractIntegrationTests;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class DestroyLinkSessionFromPlanningSessionIntegrationTest extends AbstractIntegrationTests {

    @Autowired
    private DestroyLinkSessionFromPlanningSession subject;

    @Test
    void shouldDestroyLinkFromPlanningSession() {
        final var planningPokerSession = createPlanningPokerSession();
        createLinkSession(planningPokerSession);

        subject.execute(planningPokerSession.getId());

        final var linkSessionList = linkSessionRepository.findAll();

        Assertions.assertThat(linkSessionList).isEmpty();
    }
}