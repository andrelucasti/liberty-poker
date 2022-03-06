package com.liberty.poker.planningsession;

import com.liberty.poker.AbstractIntegrationTests;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class DestroyPlanningPokerSessionIntegrationTest extends AbstractIntegrationTests{

    @Autowired
    private DestroyPlanningPokerSession subject;

    @Test
    void shouldDestroyPlanningSession() {
        final var planningPokerSession = createPlanningPokerSession();
        subject.execute(planningPokerSession.getId());

        final var planningSessionList = planningSessionRepository.findAll();
        Assertions.assertThat(planningSessionList).isEmpty();
    }
}