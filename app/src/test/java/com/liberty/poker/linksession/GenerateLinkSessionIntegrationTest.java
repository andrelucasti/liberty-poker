package com.liberty.poker.linksession;

import com.liberty.poker.AbstractIntegrationTests;
import com.liberty.poker.planningsession.PlanningSession;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class GenerateLinkSessionIntegrationTest extends AbstractIntegrationTests {

    @Autowired
    private GenerateLinkSession subject;

    @Test
    void shouldSaveLinkSession() {
        final PlanningSession planningSession = createPlanningPokerSession();

        final var linkSessionExpected = new LinkSession(planningSession.getId());

        subject.execute(linkSessionExpected);

        final var actualLinkSession = linkSessionRepository.findAll().stream().findFirst().get();

        Assertions.assertThat(linkSessionExpected.getPlanningSessionId()).isEqualTo(actualLinkSession.getPlanningSessionId());
        Assertions.assertThat(linkSessionExpected.getLink()).isEqualTo(actualLinkSession.getLink());
    }
}