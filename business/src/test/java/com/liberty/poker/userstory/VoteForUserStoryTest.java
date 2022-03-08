package com.liberty.poker.userstory;

import com.liberty.poker.memberuserstory.MemberUserStory;
import com.liberty.poker.memberuserstory.MemberUserStoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
class VoteForUserStoryTest {

    private VoteForUserStory subject;

    @Mock
    private MemberUserStoryRepository memberUserStoryRepository;

    @Mock
    private UserStoryRepository userStoryRepository;

    @BeforeEach
    void setUp() {
        subject = new VoteForUserStory(memberUserStoryRepository, userStoryRepository);
    }

    @Test
    void shouldUpdateValueFromUserStoryWhenStatusIsVoting() throws UserStoryVoteException {
        final var userStoryId = UUID.randomUUID();
        final var memberId = UUID.randomUUID();
        final var sessionId = UUID.randomUUID();
        final var value = 5;
        final var userStory = new UserStory(userStoryId, "anyDesc", UserStory.UserStoryStatus.VOTING, sessionId);

        when(userStoryRepository.findMandatoryById(eq(userStoryId))).thenReturn(userStory);

        subject.execute(memberId, userStoryId, sessionId, value);

        verify(memberUserStoryRepository).updateVoteFrom(new MemberUserStory(memberId, userStoryId, sessionId, value));
    }

    @Test
    void shouldThrowExceptionWhenTryUpdateAUserStoryWithStatusIsPending() {
        final var userStoryId = UUID.randomUUID();
        final var memberId = UUID.randomUUID();
        final var sessionId = UUID.randomUUID();
        final var value = 5;
        final var userStory = new UserStory(userStoryId, "anyDesc", UserStory.UserStoryStatus.PENDING, sessionId);

        when(userStoryRepository.findMandatoryById(eq(userStoryId))).thenReturn(userStory);

        assertThatExceptionOfType(UserStoryVoteException.class).isThrownBy(() ->
                subject.execute(memberId, userStoryId, sessionId, value))
                .withMessage("Not possible to vote when the status is PENDING, please wait to change from VOTING");
    }

    @Test
    void shouldThrowExceptionWhenTryUpdateAUserStoryWithStatusIsVoted() {
        final var userStoryId = UUID.randomUUID();
        final var memberId = UUID.randomUUID();
        final var sessionId = UUID.randomUUID();
        final var value = 5;
        final var userStory = new UserStory(userStoryId, "anyDesc", UserStory.UserStoryStatus.VOTED, sessionId);

        when(userStoryRepository.findMandatoryById(eq(userStoryId))).thenReturn(userStory);

        assertThatExceptionOfType(UserStoryVoteException.class).isThrownBy(() ->
                        subject.execute(memberId, userStoryId, sessionId, value))
                .withMessage("Not possible to vote when the status is VOTED anymore");
    }
}