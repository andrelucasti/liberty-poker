package com.liberty.poker.userstory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RemoveUserStoryFromPlanningSessionTest {

    private RemoveUserStoryFromPlanningSession subject;

    @Mock
    private UserStoryRepository userStoryRepository;
    
    @BeforeEach
    void setUp() {
        subject = new RemoveUserStoryFromPlanningSession(userStoryRepository);
    }

    @Test
    void shouldRemoveUserStoryByPlanningSessionId() {

        final var planningSessionId = UUID.randomUUID();
        subject.execute(planningSessionId);

        verify(userStoryRepository).deleteByPlanningSessionId(eq(planningSessionId));
    }
}