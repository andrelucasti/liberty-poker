package com.liberty.poker.vote;

import com.liberty.poker.member.MemberRepository;
import com.liberty.poker.memberuserstory.ConnectUserStoryToMember;
import com.liberty.poker.userstory.UserStory;
import com.liberty.poker.userstory.UserStoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.stream.Collectors;

import static com.liberty.poker.userstory.UserStory.UserStoryStatus.*;

@Service
public class StartVote {
    private final UserStoryRepository userStoryRepository;
    private final MemberRepository memberRepository;
    private final ConnectUserStoryToMember connectUserStoryToMember;

    public StartVote(final UserStoryRepository userStoryRepository,
                     final MemberRepository memberRepository,
                     final ConnectUserStoryToMember connectUserStoryToMember) {

        this.userStoryRepository = userStoryRepository;
        this.memberRepository = memberRepository;
        this.connectUserStoryToMember = connectUserStoryToMember;
    }

    @Transactional
    public void execute(final UUID planningSessionId) {
        final var userStoryPending = userStoryRepository.findByPlanningSessionId(planningSessionId)
                .stream()
                .filter(userStory -> userStory.getUserStoryStatus().equals(PENDING))
                .collect(Collectors.toList());

        userStoryPending.stream()
                .map(this::createUserStoryWithVotingStatus)
                .forEach(userStoryRepository::update);

        final var members = memberRepository.findByPlanningSessionId(planningSessionId);

        members.forEach(member -> connectUserStoryToMember
                .execute(member.getId(), userStoryPending, planningSessionId));
    }

    private UserStory createUserStoryWithVotingStatus(final UserStory userStory) {
        return new UserStory(userStory.getId(), userStory.getDescription(), VOTING, userStory.getPlanningSessionId());
    }
}
