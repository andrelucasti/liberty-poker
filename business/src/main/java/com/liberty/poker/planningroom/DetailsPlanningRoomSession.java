package com.liberty.poker.planningroom;


import com.liberty.poker.member.Member;
import com.liberty.poker.member.MemberRepository;
import com.liberty.poker.memberuserstory.MemberUserStory;
import com.liberty.poker.memberuserstory.MemberUserStoryRepository;
import com.liberty.poker.planningsession.PlanningSessionRepository;
import com.liberty.poker.userstory.UserStory;
import com.liberty.poker.userstory.UserStoryDTO;
import com.liberty.poker.userstory.UserStoryRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.liberty.poker.planningroom.MemberRoomDTO.StoryVotedDTO;

@Component
public class DetailsPlanningRoomSession {
    private final PlanningSessionRepository planningSessionRepository;
    private final MemberRepository memberRepository;
    private final UserStoryRepository userStoryRepository;
    private final MemberUserStoryRepository memberUserStoryRepository;

    public DetailsPlanningRoomSession(final PlanningSessionRepository planningSessionRepository,
                                      final MemberRepository memberRepository,
                                      final UserStoryRepository userStoryRepository,
                                      final MemberUserStoryRepository memberUserStoryRepository) {
        this.planningSessionRepository = planningSessionRepository;
        this.memberRepository = memberRepository;
        this.userStoryRepository = userStoryRepository;
        this.memberUserStoryRepository = memberUserStoryRepository;
    }

//    @Transactional(readOnly = true) // if we were working with two database, read and write.
    @Transactional
    public PlanningRoomSessionDetailsDTO execute(final UUID planningSessionId) {
        final var planningSession = planningSessionRepository.findMandatoryById(planningSessionId);
        final var members = memberRepository.findByPlanningSessionId(planningSessionId);
        final var userStories = userStoryRepository.findByPlanningSessionId(planningSessionId);

        final var memberRoomDTOS = members.stream()
                .map(member -> createMemberRoomDTO(planningSessionId, userStories, member))
                .collect(Collectors.toList());

        final var userStoryDTOS = userStories.stream()
                .map(userStory -> createUserStoryDTO(planningSessionId, userStory)).collect(Collectors.toList());

        return new PlanningRoomSessionDetailsDTO(planningSession.getTitle(), memberRoomDTOS, userStoryDTOS);
    }

    private MemberRoomDTO createMemberRoomDTO(final UUID planningSessionId,
                                              final List<UserStory> userStories, Member member) {

        final var userStoriesVotedMap = memberUserStoryRepository
                .findByMemberIdAndPlanningSessionId(member.getId(), planningSessionId)
                .stream()
                .filter(this::hasAlreadyBeenVoted)
                .collect(Collectors.toMap(MemberUserStory::getUserStoryId, MemberUserStory::getVoteValue));

        final var storyVotedDTOS = userStories.stream()
                .filter(userStory -> userStoriesVotedMap.containsKey(userStory.getId()))
                .map(userStory -> createUserStoryVotedDTO(userStory, userStoriesVotedMap.get(userStory.getId())))
                .collect(Collectors.toList());

        return new MemberRoomDTO(member.getId(), member.getNickName(), storyVotedDTOS, planningSessionId);
    }

    private StoryVotedDTO createUserStoryVotedDTO(final UserStory userStory, final Long voteValue) {
        return new StoryVotedDTO(userStory.getId(), userStory.getDescription(), userStory.getUserStoryStatus(), voteValue);
    }

    private UserStoryDTO createUserStoryDTO(final UUID planningSessionId, final UserStory userStory) {
        final var memberUserStories = memberUserStoryRepository
                .findByUserStoryIdAndPlanningSessionId(userStory.getId(), planningSessionId);

        final var memberIds = memberUserStories
                .stream()
                .filter(this::hasAlreadyBeenVoted)
                .map(MemberUserStory::getMemberId)
                .collect(Collectors.toList());

        final var summaryValue = memberUserStories.stream().map(MemberUserStory::getVoteValue).reduce(0L, Long::sum);
        return new UserStoryDTO(userStory.getId(), userStory.getDescription(), userStory.getUserStoryStatus(), memberIds, summaryValue);
    }

    private boolean hasAlreadyBeenVoted(final MemberUserStory memberUserStory) {
        return memberUserStory.getVoteValue() > 0;
    }
}
