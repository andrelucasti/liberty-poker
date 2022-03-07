package com.liberty.poker.userstory;

import com.liberty.poker.AbstractIntegrationTests;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class AddUserStoryIntegrationTest extends AbstractIntegrationTests {

    @Autowired
    private AddUserStory subject;

    @Test
    void shouldCreateUserStory() {
        final var planningSession = createPlanningSession();
        final var expectedUseStory = new UserStory("Any Story", planningSession.getId());
        subject.execute(expectedUseStory);

        final var actualUserStory = userStoryRepository.findAll().stream().findFirst().get();

        Assertions.assertThat(actualUserStory.getId()).isNotNull();
        Assertions.assertThat(actualUserStory.getDescription()).isEqualTo(expectedUseStory.getDescription());
        Assertions.assertThat(actualUserStory.getPlanningSessionId()).isEqualTo(expectedUseStory.getPlanningSessionId());
        Assertions.assertThat(actualUserStory.getUserStoryStatus()).isEqualTo(UserStory.UserStoryStatus.PENDING);
    }
}