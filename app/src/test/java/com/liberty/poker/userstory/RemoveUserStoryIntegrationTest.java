package com.liberty.poker.userstory;

import com.liberty.poker.AbstractIntegrationTests;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class RemoveUserStoryIntegrationTest extends AbstractIntegrationTests {

    @Autowired
    private RemoveUserStory subject;

    @Test
    void shouldRemoveUserStoryById() {
        final var planningSession = createPlanningSession();
        final var userStory = createUserStory(planningSession);

        subject.execute(userStory.getId());

        final var userStoryList = userStoryRepository.findAll();
        Assertions.assertThat(userStoryList).isEmpty();
    }
}