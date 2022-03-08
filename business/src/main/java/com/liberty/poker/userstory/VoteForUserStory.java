package com.liberty.poker.userstory;

import com.liberty.poker.memberuserstory.MemberUserStory;
import com.liberty.poker.memberuserstory.MemberUserStoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class VoteForUserStory {
    private final MemberUserStoryRepository memberUserStoryRepository;
    private final UserStoryRepository userStoryRepository;

    public VoteForUserStory(final MemberUserStoryRepository memberUserStoryRepository,
                            final UserStoryRepository userStoryRepository) {
        this.memberUserStoryRepository = memberUserStoryRepository;
        this.userStoryRepository = userStoryRepository;
    }

    @Transactional
    public void execute(final UUID memberId,
                        final UUID userStoryId,
                        final UUID sessionId,
                        final long value) throws UserStoryVoteException {

        final var userStory = userStoryRepository.findMandatoryById(userStoryId);

        if(userStory.getUserStoryStatus().equals(UserStory.UserStoryStatus.PENDING)){
            throw new UserStoryVoteException("Not possible to vote when the status is PENDING, please wait to change from VOTING");
        }

        if(userStory.getUserStoryStatus().equals(UserStory.UserStoryStatus.VOTED)){
            throw new UserStoryVoteException("Not possible to vote when the status is VOTED anymore");
        }
        final var memberUserStory = new MemberUserStory(memberId, userStoryId, sessionId, value);
        memberUserStoryRepository.updateVoteFrom(memberUserStory);
    }
}
