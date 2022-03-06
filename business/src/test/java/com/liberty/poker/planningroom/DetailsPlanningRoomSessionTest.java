package com.liberty.poker.planningroom;

import com.liberty.poker.member.Member;
import com.liberty.poker.member.MemberRepository;
import com.liberty.poker.planningsession.PlanningSession;
import com.liberty.poker.planningsession.PlanningSessionRepository;
import com.liberty.poker.userstory.UserStory;
import com.liberty.poker.userstory.UserStoryRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.liberty.poker.planningsession.PlanningSession.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DetailsPlanningRoomSessionTest {

    private DetailsPlanningRoomSession subject;

    @Mock
    private PlanningSessionRepository planningSessionRepository;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private UserStoryRepository userStoryRepository;


    @BeforeEach
    void setUp() {
        subject = new DetailsPlanningRoomSession(planningSessionRepository, memberRepository, userStoryRepository);
    }

    @Test
    void shouldReturnPlanningRoomDTO() {
        final var planningSessionId = UUID.randomUUID();
        final var planningTitle = "Liberty Planning Session";
        final var planningSession = new PlanningSession(planningSessionId, planningTitle, DeckType.FIBONACCI);

        final var memberList = List.of(
                new Member("Andre Lucas", planningSessionId),
                new Member("Vjay", planningSessionId),
                new Member("Alexandre Lima", planningSessionId));

        final var storyList = List.of(
                new UserStory(UUID.randomUUID(), "story1", planningSessionId),
                new UserStory(UUID.randomUUID(), "story2", planningSessionId));

        final var expectedPlanningRoomSessionDTO = new PlanningRoomSessionDTO(planningTitle, memberList, storyList);

        when(planningSessionRepository.findMandatoryById(eq(planningSessionId)))
                        .thenReturn(planningSession);

        when(memberRepository.findByPlanningSessionId(eq(planningSessionId)))
                .thenReturn(memberList);

        when(userStoryRepository.findByPlanningSessionId(eq(planningSessionId)))
                .thenReturn(storyList);

        final var actualPlanningRoomSessionDTO = subject.execute(planningSessionId);


        Assertions.assertThat(actualPlanningRoomSessionDTO).isEqualTo(expectedPlanningRoomSessionDTO);
    }
}