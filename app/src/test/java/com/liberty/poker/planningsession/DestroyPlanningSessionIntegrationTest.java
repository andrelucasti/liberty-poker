package com.liberty.poker.planningsession;

import com.liberty.poker.AbstractIntegrationTests;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class DestroyPlanningSessionIntegrationTest extends AbstractIntegrationTests{

    @Autowired
    private DestroyPlanningSession subject;

    @Test
    void shouldDestroyPlanningSession() {
        final var planningSession = createPlanningSession();
        subject.execute(planningSession.getId());

        final var planningSessionList = planningSessionRepository.findAll();
        Assertions.assertThat(planningSessionList).isEmpty();
    }
}