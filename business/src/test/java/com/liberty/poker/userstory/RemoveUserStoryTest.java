package com.liberty.poker.userstory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

@ExtendWith({MockitoExtension.class})
class RemoveUserStoryTest {

    private RemoveUserStory subject;

    @Mock
    private UserStoryRepository userStoryRepository;

    @BeforeEach
    void setUp() {
        subject = new RemoveUserStory(userStoryRepository);
    }

    @Test
    void shouldRemoveUserStoryById() {
        final var userStoryId = UUID.randomUUID();

        subject.execute(userStoryId);

        Mockito.verify(userStoryRepository).deleteById(Mockito.eq(userStoryId));
    }
}