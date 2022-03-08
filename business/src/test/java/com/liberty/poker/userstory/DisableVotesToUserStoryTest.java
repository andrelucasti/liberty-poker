package com.liberty.poker.userstory;

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

import static com.liberty.poker.userstory.UserStory.UserStoryStatus.PENDING;
import static com.liberty.poker.userstory.UserStory.UserStoryStatus.VOTED;
import static com.liberty.poker.userstory.UserStory.UserStoryStatus.VOTING;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DisableVotesToUserStoryTest {

    private DisableVotesToUserStory subject;

    @Mock
    private UserStoryRepository userStoryRepository;

    @BeforeEach
    void setUp() {
        subject = new DisableVotesToUserStory(userStoryRepository);
    }

    @Test
    void shouldUpdateUserStoryToVotedStatus() {
        final var planningSessionId = UUID.randomUUID();
        final var userStoryArgumentCaptor = ArgumentCaptor.forClass(UserStory.class);
        final var userStory = new UserStory(UUID.randomUUID(), "anyDesc", VOTING, planningSessionId);
        final var userStories = List.of(userStory);

        when(userStoryRepository.findByPlanningSessionId(eq(planningSessionId))).thenReturn(userStories);

        subject.execute(planningSessionId);

        verify(userStoryRepository).updateStatus(userStoryArgumentCaptor.capture());
        final var captorAllValues = userStoryArgumentCaptor.getAllValues();

        Assertions.assertThat(captorAllValues).hasSize(1);
        Assertions.assertThat(captorAllValues.stream().findFirst().get()).isEqualTo(new UserStory(userStory.getId(), userStory.getDescription(), VOTED, planningSessionId));
    }
}