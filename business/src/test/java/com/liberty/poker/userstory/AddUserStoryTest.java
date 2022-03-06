package com.liberty.poker.userstory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class AddUserStoryTest {

    private AddUserStory subject;

    @Mock
    private UserStoryRepository userStoryRepository;

    @BeforeEach
    void setUp() {
        subject = new AddUserStory(userStoryRepository);
    }

    @Test
    void shouldAddUserStory() {
        final var planningSessionId = UUID.randomUUID();
        final var userStory = new UserStory("Story 1 about story 1", planningSessionId);

        subject.execute(userStory);
        Mockito.verify(userStoryRepository).save(userStory);
    }
}