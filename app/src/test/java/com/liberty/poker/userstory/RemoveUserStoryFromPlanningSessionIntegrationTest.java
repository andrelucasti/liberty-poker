package com.liberty.poker.userstory;

import com.liberty.poker.AbstractIntegrationTests;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class RemoveUserStoryFromPlanningSessionIntegrationTest extends AbstractIntegrationTests {

    @Autowired
    private RemoveUserStoryFromPlanningSession subject;

    @Test
    void shouldRemoveUserStory() {
        final var planningSession = createPlanningSession();
        createUserStory(planningSession);

        subject.execute(planningSession.getId());

        final var storyList = userStoryRepository.findAll();
        Assertions.assertThat(storyList).isEmpty();
    }

    @Test
    void shouldRemoveUserStoryFromTheSamePlanningSession() {
        final var planningSession1 = createPlanningSession();
        final var planningSession2 = createPlanningSession();

        final var userStory1 = createUserStory(planningSession1);
        final var userStory3 = createUserStory(planningSession1);
        final var userStory2 = createUserStory(planningSession2);
        final var userStory4 = createUserStory(planningSession2);

        subject.execute(planningSession1.getId());

        final var storyList = userStoryRepository.findAll();
        Assertions.assertThat(storyList).isNotEmpty();
        Assertions.assertThat(storyList).doesNotContain(userStory1, userStory3);
        Assertions.assertThat(storyList).contains(userStory2, userStory4);
    }
}