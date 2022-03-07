package com.liberty.poker.planningroom;

import com.liberty.poker.member.Member;
import com.liberty.poker.member.MemberRepository;
import com.liberty.poker.memberuserstory.MemberUserStory;
import com.liberty.poker.memberuserstory.MemberUserStoryRepository;
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
import java.util.UUID;

import static com.liberty.poker.planningsession.PlanningSession.*;
import static com.liberty.poker.userstory.UserStory.UserStoryStatus.*;
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

    @Mock
    private MemberUserStoryRepository memberUserStoryRepository;


    @BeforeEach
    void setUp() {
        subject = new DetailsPlanningRoomSession(planningSessionRepository, memberRepository, userStoryRepository, memberUserStoryRepository);
    }

    @Test
    void shouldReturnPlanningRoomDetailsDTO() {
        final var planningSessionId = UUID.randomUUID();
        final var planningTitle = "Liberty Planning Session";
        final var planningSession = new PlanningSession(planningSessionId, planningTitle, DeckType.FIBONACCI);

        final var memberId1 = UUID.randomUUID();
        final var memberId2 = UUID.randomUUID();
        final var memberId3 = UUID.randomUUID();

        final var memberNickName1 = "Andre Lucas";
        final var memberNickName2 = "Vjay";
        final var memberNickName3 = "Alexandre Lima";
        final var memberList = List.of(
                new Member(memberId1, memberNickName1, planningSessionId),
                new Member(memberId2, memberNickName2, planningSessionId),
                new Member(memberId3, memberNickName3, planningSessionId));

        final var userStoryId1 = UUID.randomUUID();
        final var userStoryId2 = UUID.randomUUID();

        final var storyList = List.of(
                new UserStory(userStoryId1, "story1", VOTING, planningSessionId),
                new UserStory(userStoryId2, "story2", VOTING, planningSessionId));

        final var memberUserStories = List.of(new MemberUserStory(memberId1, userStoryId1, planningSessionId), new MemberUserStory(memberId1, userStoryId2, planningSessionId),
                new MemberUserStory(memberId2, userStoryId1, planningSessionId), new MemberUserStory(memberId2, userStoryId2, planningSessionId),
                new MemberUserStory(memberId3, userStoryId1, planningSessionId), new MemberUserStory(memberId3, userStoryId2, planningSessionId));

        final var memberRoomDTOS = List.of(new MemberRoomDTO(memberId1, memberNickName1, storyList, planningSessionId),
                new MemberRoomDTO(memberId2, memberNickName2, storyList, planningSessionId),
                new MemberRoomDTO(memberId3, memberNickName3, storyList, planningSessionId));

        final var expectedPlanningRoomSessionDetailsDTO = new PlanningRoomSessionDetailsDTO(planningTitle, memberRoomDTOS, storyList);

        when(planningSessionRepository.findMandatoryById(eq(planningSessionId)))
                        .thenReturn(planningSession);

        when(memberRepository.findByPlanningSessionId(eq(planningSessionId)))
                .thenReturn(memberList);

        when(userStoryRepository.findByPlanningSessionId(eq(planningSessionId)))
                .thenReturn(storyList);

        memberList.forEach(member -> when(memberUserStoryRepository.findByMemberIdAndPlanningSessionId(eq(member.getId()), eq(planningSessionId)))
                .thenReturn(memberUserStories));

        final var actualPlanningRoomSessionDetailsDTO = subject.execute(planningSessionId);

        Assertions.assertThat(actualPlanningRoomSessionDetailsDTO).isEqualTo(expectedPlanningRoomSessionDetailsDTO);
    }
}