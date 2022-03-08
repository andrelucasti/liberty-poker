package com.liberty.poker.userstory;

import com.liberty.poker.member.Member;
import com.liberty.poker.member.MemberRepository;
import com.liberty.poker.memberuserstory.ConnectUserStoryToMember;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.liberty.poker.userstory.UserStory.UserStoryStatus.PENDING;
import static com.liberty.poker.userstory.UserStory.UserStoryStatus.VOTED;
import static com.liberty.poker.userstory.UserStory.UserStoryStatus.VOTING;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EnableVotesToUserStoryTest {

    private EnableVotesToUserStory subject;

    @Mock
    private UserStoryRepository userStoryRepository;
    
    @Mock
    private MemberRepository memberRepository;

    @Mock
    private ConnectUserStoryToMember connectUserStoryToMember;

    @BeforeEach
    void setUp() {
        subject = new EnableVotesToUserStory(userStoryRepository, memberRepository, connectUserStoryToMember);
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

        verify(userStoryRepository).updateStatus(new UserStory(userStoryId, userStoryDesc, VOTING, planningSessionId));
    }

    @Test
    void shouldEnableVotesJustUserStoryThatAreDifferentVotingAndVoted() {
        final var userStoryArgumentCaptor = ArgumentCaptor.forClass(UserStory.class);
        final var planningSessionId = UUID.randomUUID();

        final var userStoryPending = new UserStory(UUID.randomUUID(), "userStoryPending", PENDING, planningSessionId);
        final var userStoryVoting = new UserStory(UUID.randomUUID(), "userStoryVoting", VOTING, planningSessionId);
        final var userStoryVoted = new UserStory(UUID.randomUUID(), "userStoryVoted", VOTED, planningSessionId);

        when(userStoryRepository.findByPlanningSessionId(eq(planningSessionId)))
                .thenReturn(List.of(userStoryPending, userStoryVoting, userStoryVoted));

        subject.execute(planningSessionId);

        verify(userStoryRepository, times(2)).updateStatus(userStoryArgumentCaptor.capture());
        final var storyList = userStoryArgumentCaptor.getAllValues();
        Assertions.assertThat(storyList).hasSize(2);
        Assertions.assertThat(storyList.stream().map(UserStory::getId).collect(Collectors.toList())).contains(userStoryPending.getId(), userStoryVoting.getId());
        Assertions.assertThat(storyList.stream().map(UserStory::getId).collect(Collectors.toList())).doesNotContain(userStoryVoted.getId());
        Assertions.assertThat(storyList.stream().map(UserStory::getUserStoryStatus).collect(Collectors.toList())).contains(VOTING, VOTING);
        Assertions.assertThat(storyList.stream().map(UserStory::getUserStoryStatus).collect(Collectors.toList())).doesNotContain(VOTED);
    }

    @Test
    void shouldConnectAllUserStoriesToAllMembersInPlanningSession() {
        final var planningSessionId = UUID.randomUUID();
        final var userStoryId = UUID.randomUUID();
        final var userStoryDesc = "userStoryPending";
        final var userStoryPending = new UserStory(userStoryId, userStoryDesc, PENDING, planningSessionId);
        
        final var member = new Member(UUID.randomUUID(), "Andre Lucas", planningSessionId);

        when(userStoryRepository.findByPlanningSessionId(eq(planningSessionId)))
                .thenReturn(List.of(userStoryPending));
        when(memberRepository.findByPlanningSessionId(eq(planningSessionId))).
                thenReturn(List.of(member));
        

        subject.execute(planningSessionId);
        verify(connectUserStoryToMember).execute(eq(member.getId()), eq(List.of(userStoryPending)), eq(planningSessionId));
    }

    @Test
    void shouldConnectUserStoriesThatArePendingToAllMembersInPlanningSession() {
        final var memberIdArgumentCaptor = ArgumentCaptor.forClass(UUID.class);
        final var planningSessionId = UUID.randomUUID();

        final var userStoryPending1 = new UserStory(UUID.randomUUID(), "userStoryPending1", PENDING, planningSessionId);
        final var userStoryPending2 = new UserStory(UUID.randomUUID(), "userStoryPending2", PENDING, planningSessionId);
        final var userStory1 = new UserStory(UUID.randomUUID(), "userStory1", VOTING, planningSessionId);
        final var userStory2 = new UserStory(UUID.randomUUID(), "userStory2", VOTED, planningSessionId);

        final var member1 = new Member(UUID.randomUUID(), "Andre Lucas", planningSessionId);
        final var member2 = new Member(UUID.randomUUID(), "Vjay", planningSessionId);

        when(userStoryRepository.findByPlanningSessionId(eq(planningSessionId)))
                .thenReturn(List.of(userStoryPending1, userStoryPending2, userStory1, userStory2));
        when(memberRepository.findByPlanningSessionId(eq(planningSessionId))).
                thenReturn(List.of(member1, member2));

        subject.execute(planningSessionId);
        verify(connectUserStoryToMember, times(2)).execute(memberIdArgumentCaptor.capture(), eq(List.of(userStoryPending1, userStoryPending2)), eq(planningSessionId));

        Assertions.assertThat(memberIdArgumentCaptor.getAllValues()).hasSize(2);
        Assertions.assertThat(memberIdArgumentCaptor.getAllValues()).contains(member1.getId(), member2.getId());
    }
}