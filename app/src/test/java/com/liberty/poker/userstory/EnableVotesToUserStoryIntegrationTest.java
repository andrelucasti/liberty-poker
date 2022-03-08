package com.liberty.poker.userstory;

import com.google.common.collect.Iterables;
import com.liberty.poker.AbstractIntegrationTests;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class EnableVotesToUserStoryIntegrationTest extends AbstractIntegrationTests {

    @Autowired
    private EnableVotesToUserStory subject;

    @Test
    void shouldUpdateUserStoryStatusWhenEnableVotesFromPendingToVoting() {
        final var planningSession = createPlanningSession();
        final var userStory = createUserStory(planningSession);
        Assertions.assertThat(userStory.getUserStoryStatus()).isEqualTo(UserStory.UserStoryStatus.PENDING);

        subject.execute(planningSession.getId());

        final var userStoryList = Iterables.getOnlyElement(userStoryRepository.findAll());
        Assertions.assertThat(userStoryList.getUserStoryStatus()).isEqualTo(UserStory.UserStoryStatus.VOTING);
    }
}