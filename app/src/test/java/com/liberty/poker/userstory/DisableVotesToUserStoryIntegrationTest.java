package com.liberty.poker.userstory;

import com.liberty.poker.AbstractIntegrationTests;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class DisableVotesToUserStoryIntegrationTest extends AbstractIntegrationTests {

    @Autowired
    private DisableVotesToUserStory subject;

    @Test
    void shouldDisableVotesFromTheUserStory() {
        final var planningSession = createPlanningSession();

        final var userStory1 = createUserStory(planningSession, UserStory.UserStoryStatus.PENDING);
        final var userStory2 = createUserStory(planningSession, UserStory.UserStoryStatus.VOTING);
        final var userStory3 = createUserStory(planningSession, UserStory.UserStoryStatus.VOTED);

        subject.execute(planningSession.getId());

        final var userStoryList = userStoryRepository.findByPlanningSessionId(planningSession.getId());

        Assertions.assertThat(userStoryList).hasSize(3);
        Assertions.assertThat(userStoryList).contains(
                new UserStory(userStory1.getId(), userStory1.getDescription(), UserStory.UserStoryStatus.VOTED, planningSession.getId()),
                new UserStory(userStory2.getId(), userStory2.getDescription(), UserStory.UserStoryStatus.VOTED, planningSession.getId()),
                new UserStory(userStory3.getId(), userStory3.getDescription(), UserStory.UserStoryStatus.VOTED, planningSession.getId())
        );
    }
}