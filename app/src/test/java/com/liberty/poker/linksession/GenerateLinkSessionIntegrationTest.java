package com.liberty.poker.linksession;

import com.liberty.poker.planningsession.PlanningSession;
import com.liberty.poker.planningsession.PlanningSessionRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GenerateLinkSessionIntegrationTest {

    @Autowired
    private GenerateLinkSession subject;

    @Autowired
    private LinkSessionRepository linkSessionRepository;

    @Autowired
    private PlanningSessionRepository planningSessionRepository;

    @Test
    void shouldSaveLinkSession() {
        final var planningSession = planningSessionRepository.save(
                new PlanningSession("LibertyPlanningPokerSession", PlanningSession.DeckType.FIBONACCI));

        final var linkSessionExpected = new LinkSession(planningSession.getId());

        subject.execute(linkSessionExpected);

        final var actualLinkSession = linkSessionRepository.findAll().stream().findFirst().get();

        Assertions.assertThat(linkSessionExpected.getPlanningSessionId()).isEqualTo(actualLinkSession.getPlanningSessionId());
        Assertions.assertThat(linkSessionExpected.getLink()).isEqualTo(actualLinkSession.getLink());
    }
}