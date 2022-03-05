package com.liberty.poker.planningsession;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static com.liberty.poker.planningsession.PlanningSession.DeckType.FIBONACCI;

@SpringBootTest
class CreatePlanningPokerSessionIntegrationTest {

    @Autowired
    private CreatePlanningPokerSession subject;

    @Autowired
    private PlanningSessionRepository planningSessionRepository;


    @Test
    void shouldSavePlanningPokerSession() {
        final var planningSessionExpected = new PlanningSession("LibertyPlanningPokerSession", FIBONACCI);
        subject.execute(planningSessionExpected);

        final var actualPlanningSession = planningSessionRepository.findAll().stream().findFirst().get();


        Assertions.assertThat(actualPlanningSession.getTitle()).isEqualTo(planningSessionExpected.getTitle());
        Assertions.assertThat(actualPlanningSession.getDeckType()).isEqualTo(planningSessionExpected.getDeckType());
    }
}