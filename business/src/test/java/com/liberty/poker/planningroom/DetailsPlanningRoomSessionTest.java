package com.liberty.poker.planningroom;

import com.liberty.poker.member.Member;
import com.liberty.poker.member.MemberRepository;
import com.liberty.poker.memberuserstory.MemberUserStory;
import com.liberty.poker.memberuserstory.MemberUserStoryRepository;
import com.liberty.poker.planningsession.PlanningSession;
import com.liberty.poker.planningsession.PlanningSessionRepository;
import com.liberty.poker.userstory.UserStory;
import com.liberty.poker.userstory.UserStoryDTO;
import com.liberty.poker.userstory.UserStoryRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.liberty.poker.planningsession.PlanningSession.DeckType;
import static com.liberty.poker.userstory.UserStory.UserStoryStatus.PENDING;
import static com.liberty.poker.userstory.UserStory.UserStoryStatus.VOTED;
import static com.liberty.poker.userstory.UserStory.UserStoryStatus.VOTING;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;

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
        final var planningSession = new PlanningSession(UUID.randomUUID(), "Liberty Challenger", DeckType.FIBONACCI);
        final var planningSessionId = planningSession.getId();

        final var member1 = new Member(UUID.randomUUID(), "Andre Lucas", planningSessionId);
        final var member2 = new Member(UUID.randomUUID(), "Vjay", planningSessionId);
        final var member3 = new Member(UUID.randomUUID(), "Alexandre Lima", planningSessionId);

        final var userStory1 = new UserStory(UUID.randomUUID(), "anyDesc1", PENDING, planningSessionId);
        final var userStory2 = new UserStory(UUID.randomUUID(), "anyDesc2", PENDING, planningSessionId);

        final var memberUserStory1 = new MemberUserStory(member1.getId(), userStory1.getId(), planningSessionId, 1);
        final var memberUserStory2 = new MemberUserStory(member1.getId(), userStory2.getId(), planningSessionId);
        final var memberUserStory3 = new MemberUserStory(member2.getId(), userStory1.getId(), planningSessionId);
        final var memberUserStory4 = new MemberUserStory(member2.getId(), userStory2.getId(), planningSessionId, 1);
        final var memberUserStory5 = new MemberUserStory(member3.getId(), userStory1.getId(), planningSessionId, 1);
        final var memberUserStory6 = new MemberUserStory(member3.getId(), userStory2.getId(), planningSessionId);

        final var memberUserStories = List.of(memberUserStory1, memberUserStory2, memberUserStory3, memberUserStory4, memberUserStory5, memberUserStory6);

        final var storyVotedDTO1 = new MemberRoomDTO.StoryVotedDTO(userStory1.getId(), userStory1.getDescription(), userStory1.getUserStoryStatus(), memberUserStory1.getVoteValue());
        final var storyVotedDTO2 = new MemberRoomDTO.StoryVotedDTO(userStory2.getId(), userStory2.getDescription(), userStory2.getUserStoryStatus(), memberUserStory4.getVoteValue());
        final var storyVotedDTO3 = new MemberRoomDTO.StoryVotedDTO(userStory1.getId(), userStory1.getDescription(), userStory1.getUserStoryStatus(), memberUserStory5.getVoteValue());

        final var memberRoomDTOS = List.of(
                new MemberRoomDTO(member1.getId(), member1.getNickName(), List.of(storyVotedDTO1), planningSessionId),
                new MemberRoomDTO(member2.getId(), member2.getNickName(), List.of(storyVotedDTO2), planningSessionId),
                new MemberRoomDTO(member3.getId(), member3.getNickName(), List.of(storyVotedDTO3), planningSessionId));

        final var userStoryDTOS = List.of(
                new UserStoryDTO(userStory1.getId(), userStory1.getDescription(), userStory1.getUserStoryStatus(), List.of(member1.getId(), member3.getId()), 2L),
                new UserStoryDTO(userStory2.getId(), userStory2.getDescription(), userStory2.getUserStoryStatus(), List.of(member2.getId()), 1L));

        final var expectedPlanningRoomSessionDetailsDTO = new PlanningRoomSessionDetailsDTO(planningSession.getTitle(), memberRoomDTOS, userStoryDTOS);

        when(planningSessionRepository.findMandatoryById(eq(planningSessionId)))
                .thenReturn(planningSession);

        when(memberRepository.findByPlanningSessionId(eq(planningSessionId)))
                .thenReturn(List.of(member1, member2, member3));

        when(userStoryRepository.findByPlanningSessionId(eq(planningSessionId)))
                .thenReturn(List.of(userStory1, userStory2));

        final var memberUserStories1 = memberUserStories.stream().filter(memberUserStory -> memberUserStory.getMemberId().equals(member1.getId())).collect(Collectors.toList());
        when(memberUserStoryRepository.findByMemberIdAndPlanningSessionId(eq(member1.getId()), eq(planningSessionId)))
                .thenReturn(memberUserStories1);

        final var memberUserStories2 = memberUserStories.stream().filter(memberUserStory -> memberUserStory.getMemberId().equals(member2.getId())).collect(Collectors.toList());
        when(memberUserStoryRepository.findByMemberIdAndPlanningSessionId(eq(member2.getId()), eq(planningSessionId)))
                .thenReturn(memberUserStories2);

        final var memberUserStories3 = memberUserStories.stream().filter(memberUserStory -> memberUserStory.getMemberId().equals(member3.getId())).collect(Collectors.toList());
        when(memberUserStoryRepository.findByMemberIdAndPlanningSessionId(eq(member3.getId()), eq(planningSessionId)))
                .thenReturn(memberUserStories3);

        final var userStoryMembers1 = memberUserStories.stream().filter(memberUserStory -> memberUserStory.getUserStoryId().equals(userStory1.getId())).collect(Collectors.toList());
        when(memberUserStoryRepository.findByUserStoryIdAndPlanningSessionId(eq(userStory1.getId()), eq(planningSessionId)))
                .thenReturn(userStoryMembers1);

        final var userStoryMembers2 = memberUserStories.stream().filter(memberUserStory -> memberUserStory.getUserStoryId().equals(userStory2.getId())).collect(Collectors.toList());
        when(memberUserStoryRepository.findByUserStoryIdAndPlanningSessionId(eq(userStory2.getId()), eq(planningSessionId)))
                .thenReturn(userStoryMembers2);

        final var actualPlanningRoomSessionDetailsDTO = subject.execute(planningSessionId);

        Assertions.assertThat(actualPlanningRoomSessionDetailsDTO).isEqualTo(expectedPlanningRoomSessionDetailsDTO);
    }

    @Test
    void shouldDoShowTheVoteValueOfTheMembersInThePlanningSessionWhenAllUserStoryAreVotedStatus() {
        final var planningSessionId = UUID.randomUUID();
        final var planningSession = new PlanningSession(planningSessionId, "Liberty Title", DeckType.FIBONACCI);
        final var member = new Member(UUID.randomUUID(), "Andre Lucas", planningSessionId);

        final var userStory1 = new UserStory(UUID.randomUUID(), "anyDesc", VOTED, planningSessionId);
        final var userStory2 = new UserStory(UUID.randomUUID(), "anyDesc", VOTED, planningSessionId);

        final var memberUserStory1 = new MemberUserStory(member.getId(), userStory1.getId(), planningSessionId, 5);
        final var memberUserStory2 = new MemberUserStory(member.getId(), userStory2.getId(), planningSessionId, 10);

        when(planningSessionRepository.findMandatoryById(eq(planningSessionId)))
                .thenReturn(planningSession);

        when(userStoryRepository.findByPlanningSessionId(eq(planningSessionId)))
                .thenReturn(List.of(userStory1, userStory2));

        when(memberRepository.findByPlanningSessionId(eq(planningSessionId)))
                .thenReturn(List.of(member));

        when(memberUserStoryRepository.findByMemberIdAndPlanningSessionId(member.getId(), planningSessionId))
                .thenReturn(List.of(memberUserStory1, memberUserStory2));

        final var userStoryMembers1 = Stream.of(memberUserStory1, memberUserStory2)
                .filter(memberUserStory -> memberUserStory.getUserStoryId().equals(userStory1.getId())).collect(Collectors.toList());
        when(memberUserStoryRepository.findByUserStoryIdAndPlanningSessionId(eq(userStory1.getId()), eq(planningSessionId)))
                .thenReturn(userStoryMembers1);

        final var userStoryMembers2 = Stream.of(memberUserStory1, memberUserStory2)
                .filter(memberUserStory -> memberUserStory.getUserStoryId().equals(userStory2.getId())).collect(Collectors.toList());
        when(memberUserStoryRepository.findByUserStoryIdAndPlanningSessionId(eq(userStory2.getId()), eq(planningSessionId)))
                .thenReturn(userStoryMembers2);

        final var actualPlanningRoomSessionDetailsDTO = subject.execute(planningSessionId);

        final var storyVotedDTO1 = new MemberRoomDTO.StoryVotedDTO(userStory1.getId(), userStory1.getDescription(), userStory1.getUserStoryStatus(), memberUserStory1.getVoteValue());
        final var storyVotedDTO2 = new MemberRoomDTO.StoryVotedDTO(userStory2.getId(), userStory2.getDescription(), userStory2.getUserStoryStatus(), memberUserStory2.getVoteValue());

        final var memberRoomDTOS = List.of(new MemberRoomDTO(member.getId(), member.getNickName(), List.of(storyVotedDTO1, storyVotedDTO2), planningSessionId));

        final var userStoryDTOS = List.of(
                new UserStoryDTO(userStory1.getId(), userStory1.getDescription(), userStory1.getUserStoryStatus(), List.of(member.getId()), 5L),
                new UserStoryDTO(userStory2.getId(), userStory2.getDescription(), userStory2.getUserStoryStatus(), List.of(member.getId()), 10L));

        final var expectedPlanningRoomSessionDetailsDTO = new PlanningRoomSessionDetailsDTO(planningSession.getTitle(), memberRoomDTOS, userStoryDTOS);

        Assertions.assertThat(actualPlanningRoomSessionDetailsDTO).isEqualTo(expectedPlanningRoomSessionDetailsDTO);
    }

    @Test
    void shouldDoNotShowTheVoteValueOfTheMembersInThePlanningSessionWhenAllUserStoryAreNotVotedYet() {
        final var planningSessionId = UUID.randomUUID();
        final var planningSession = new PlanningSession(planningSessionId, "Liberty Title", DeckType.FIBONACCI);
        final var member = new Member(UUID.randomUUID(), "Andre Lucas", planningSessionId);

        final var userStory1 = new UserStory(UUID.randomUUID(), "anyDesc", PENDING, planningSessionId);
        final var userStory2 = new UserStory(UUID.randomUUID(), "anyDesc", VOTING, planningSessionId);

        final var memberUserStory1 = new MemberUserStory(member.getId(), userStory1.getId(), planningSessionId, 5);
        final var memberUserStory2 = new MemberUserStory(member.getId(), userStory2.getId(), planningSessionId, 10);

        when(planningSessionRepository.findMandatoryById(eq(planningSessionId)))
                .thenReturn(planningSession);

        when(userStoryRepository.findByPlanningSessionId(eq(planningSessionId)))
                .thenReturn(List.of(userStory1, userStory2));

        when(memberRepository.findByPlanningSessionId(eq(planningSessionId)))
                .thenReturn(List.of(member));

        when(memberUserStoryRepository.findByMemberIdAndPlanningSessionId(member.getId(), planningSessionId))
                .thenReturn(List.of(memberUserStory1, memberUserStory2));

        final var userStoryMembers1 = Stream.of(memberUserStory1, memberUserStory2)
                .filter(memberUserStory -> memberUserStory.getUserStoryId().equals(userStory1.getId())).collect(Collectors.toList());
        when(memberUserStoryRepository.findByUserStoryIdAndPlanningSessionId(eq(userStory1.getId()), eq(planningSessionId)))
                .thenReturn(userStoryMembers1);

        final var userStoryMembers2 = Stream.of(memberUserStory1, memberUserStory2)
                .filter(memberUserStory -> memberUserStory.getUserStoryId().equals(userStory2.getId())).collect(Collectors.toList());
        when(memberUserStoryRepository.findByUserStoryIdAndPlanningSessionId(eq(userStory2.getId()), eq(planningSessionId)))
                .thenReturn(userStoryMembers2);

        final var actualPlanningRoomSessionDetailsDTO = subject.execute(planningSessionId);

        final var storyVotedDTO1 = new MemberRoomDTO.StoryVotedDTO(userStory1.getId(), userStory1.getDescription(), userStory1.getUserStoryStatus(), null);
        final var storyVotedDTO2 = new MemberRoomDTO.StoryVotedDTO(userStory2.getId(), userStory2.getDescription(), userStory2.getUserStoryStatus(), null);

        final var memberRoomDTOS = List.of(new MemberRoomDTO(member.getId(), member.getNickName(), List.of(storyVotedDTO1, storyVotedDTO2), planningSessionId));

        final var userStoryDTOS = List.of(
                new UserStoryDTO(userStory1.getId(), userStory1.getDescription(), userStory1.getUserStoryStatus(), List.of(member.getId()), null),
                new UserStoryDTO(userStory2.getId(), userStory2.getDescription(), userStory2.getUserStoryStatus(), List.of(member.getId()), null));

        final var expectedPlanningRoomSessionDetailsDTO = new PlanningRoomSessionDetailsDTO(planningSession.getTitle(), memberRoomDTOS, userStoryDTOS);

        Assertions.assertThat(actualPlanningRoomSessionDetailsDTO).isEqualTo(expectedPlanningRoomSessionDetailsDTO);
    }
}