package com.liberty.poker.memberuserstory;

import com.liberty.poker.member.Member;
import com.liberty.poker.userstory.UserStory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class ConnectUserStoryToMemberTest {

    private ConnectUserStoryToMember subject;

    @Mock
    private MemberUserStoryRepository memberUserStoryRepository;

    @BeforeEach
    void setUp() {
        subject = new ConnectUserStoryToMember(memberUserStoryRepository);
    }

    @Test
    void shouldSaveUserStoriesToMemberInPlanningRoomSession() {
        final var memberUserStoryCaptor = ArgumentCaptor.forClass(MemberUserStory.class);
        final var planningSessionId = UUID.randomUUID();
        final var member = new Member(UUID.randomUUID(), "Andre Lucas", planningSessionId);
        final var userStory1 = new UserStory(UUID.randomUUID(), "anyDesc1", UserStory.UserStoryStatus.VOTING, planningSessionId);
        final var userStory2 = new UserStory(UUID.randomUUID(), "anyDesc2", UserStory.UserStoryStatus.VOTING, planningSessionId);

        subject.execute(member.getId(), List.of(userStory1, userStory2), planningSessionId);

        verify(memberUserStoryRepository, times(2)).save(memberUserStoryCaptor.capture());

        Assertions.assertThat(memberUserStoryCaptor.getAllValues()).contains(
                new MemberUserStory(member.getId(), userStory1.getId(), planningSessionId),
                new MemberUserStory(member.getId(), userStory2.getId(), planningSessionId));
    }
}