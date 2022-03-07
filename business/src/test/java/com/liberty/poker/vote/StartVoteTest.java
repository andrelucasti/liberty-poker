package com.liberty.poker.vote;

import com.liberty.poker.userstory.UserStory;
import com.liberty.poker.userstory.UserStoryRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static com.liberty.poker.userstory.UserStory.UserStoryStatus.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StartVoteTest {

    private StartVote subject;

    @Mock
    private UserStoryRepository userStoryRepository;

    @BeforeEach
    void setUp() {
        subject = new StartVote(userStoryRepository);
    }

    @Test
    void shouldEnableVotesInPlanningSession() {
        final var planningSessionId = UUID.randomUUID();
        final var userStoryId = UUID.randomUUID();
        final var userStoryDesc = "anyDesc";

        final var userStory = new UserStory(userStoryId, userStoryDesc, PENDING, planningSessionId);
        when(userStoryRepository.findByPlanningSessionId(eq(planningSessionId)))
                        .thenReturn(List.of(userStory));

        subject.execute(planningSessionId);

        verify(userStoryRepository).update(new UserStory(userStoryId, userStoryDesc, VOTING, planningSessionId));
    }

    @Test
    void shouldEnableVotesJustUserStoryThatAreDifferentVotingAndVoted() {
        final var userStoryArgumentCaptor = ArgumentCaptor.forClass(UserStory.class);
        final var planningSessionId = UUID.randomUUID();
        final var userStoryId = UUID.randomUUID();
        final var userStoryDesc = "userStoryPending";

        final var userStoryPending = new UserStory(userStoryId, userStoryDesc, PENDING, planningSessionId);
        final var userStoryVoting = new UserStory(UUID.randomUUID(), "userStoryVoting", VOTING, planningSessionId);
        final var userStoryVoted = new UserStory(UUID.randomUUID(), "userStoryVoted", VOTED, planningSessionId);

        when(userStoryRepository.findByPlanningSessionId(eq(planningSessionId)))
                .thenReturn(List.of(userStoryPending, userStoryVoting, userStoryVoted));

        subject.execute(planningSessionId);

        verify(userStoryRepository, times(1)).update(userStoryArgumentCaptor.capture());
        final var storyList = userStoryArgumentCaptor.getAllValues();
        Assertions.assertThat(storyList).hasSize(1);
        Assertions.assertThat(storyList.stream().findFirst().get()).isEqualTo(new UserStory(userStoryId, userStoryDesc, VOTING, planningSessionId));
    }
}