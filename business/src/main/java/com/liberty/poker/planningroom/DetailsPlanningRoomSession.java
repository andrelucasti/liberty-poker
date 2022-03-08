package com.liberty.poker.planningroom;


import com.liberty.poker.member.MemberRepository;
import com.liberty.poker.memberuserstory.MemberUserStory;
import com.liberty.poker.memberuserstory.MemberUserStoryRepository;
import com.liberty.poker.planningsession.PlanningSessionRepository;
import com.liberty.poker.userstory.UserStoryDTO;
import com.liberty.poker.userstory.UserStoryRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.stream.Collectors;

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
                .map(member -> {
                    final var userStoriesVoted = memberUserStoryRepository
                            .findByMemberIdAndPlanningSessionId(member.getId(), planningSessionId)
                            .stream()
                            .filter(this::hasAlreadyBeenVoted)
                            .map(MemberUserStory::getUserStoryId)
                            .collect(Collectors.toList());
                    return new MemberRoomDTO(member.getId(), member.getNickName(), userStoriesVoted, planningSessionId);
                }).collect(Collectors.toList());


        final var userStoryDTOS = userStories.stream()
                .map(userStory -> {
                    final var memberIds = memberUserStoryRepository
                            .findByUserStoryIdAndPlanningSessionId(userStory.getId(), planningSessionId)
                            .stream()
                            .filter(this::hasAlreadyBeenVoted)
                            .map(MemberUserStory::getMemberId)
                            .collect(Collectors.toList());

                    return new UserStoryDTO(userStory.getId(), userStory.getDescription(), userStory.getUserStoryStatus(), memberIds);
                }).collect(Collectors.toList());


        return new PlanningRoomSessionDetailsDTO(planningSession.getTitle(), memberRoomDTOS, userStoryDTOS);
    }

    private boolean hasAlreadyBeenVoted(final MemberUserStory memberUserStory) {
        return memberUserStory.getValueValue() > 0;
    }
}
