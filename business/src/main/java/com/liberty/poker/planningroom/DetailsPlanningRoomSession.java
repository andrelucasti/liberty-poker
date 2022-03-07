package com.liberty.poker.planningroom;


import com.liberty.poker.member.MemberRepository;
import com.liberty.poker.memberuserstory.MemberUserStory;
import com.liberty.poker.memberuserstory.MemberUserStoryRepository;
import com.liberty.poker.planningsession.PlanningSessionRepository;
import com.liberty.poker.userstory.UserStoryRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
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
    public PlanningRoomSessionDTO execute(final UUID planningSessionId) {
        final var planningSession = planningSessionRepository.findMandatoryById(planningSessionId);
        final var members = memberRepository.findByPlanningSessionId(planningSessionId);
        final var userStories = userStoryRepository.findByPlanningSessionId(planningSessionId);

        final var memberRoomDTOS = members.stream()
                .map(member -> {
                    final var userStoriesId = memberUserStoryRepository
                            .findByMemberIdAndPlanningSessionId(member.getId(), planningSessionId)
                            .stream()
                            .map(MemberUserStory::getUserStoryId)
                            .collect(Collectors.toList());
//                            .map(memberUserStory -> Map.<UUID, Long>of()).collect(Collectors.toList());

                    final var userStoryList = userStories
                            .stream()
                            .filter(userStory -> userStoriesId.contains(userStory.getId()))
                            .collect(Collectors.toList());


                    return new MemberRoomDTO(member.getId(), member.getNickName(), userStoryList, planningSessionId);
                }).collect(Collectors.toList());
        return new PlanningRoomSessionDTO(planningSession.getTitle(), memberRoomDTOS, Collections.emptyList(), userStories);
    }
}
