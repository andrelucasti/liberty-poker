package com.liberty.poker.planningroom;


import com.liberty.poker.member.MemberRepository;
import com.liberty.poker.planningsession.PlanningSessionRepository;
import com.liberty.poker.userstory.UserStoryRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
public class DetailsPlanningRoomSession {
    private final PlanningSessionRepository planningSessionRepository;
    private final MemberRepository memberRepository;
    private final UserStoryRepository userStoryRepository;

    public DetailsPlanningRoomSession(final PlanningSessionRepository planningSessionRepository,
                                      final MemberRepository memberRepository,
                                      final UserStoryRepository userStoryRepository) {
        this.planningSessionRepository = planningSessionRepository;
        this.memberRepository = memberRepository;
        this.userStoryRepository = userStoryRepository;
    }

//    @Transactional(readOnly = true) // if we were working with two database, read and write.
    @Transactional
    public PlanningRoomSessionDTO execute(final UUID planningSessionId) {
        final var planningSession = planningSessionRepository.findMandatoryById(planningSessionId);
        final var members = memberRepository.findByPlanningSessionId(planningSessionId);
        final var userStories = userStoryRepository.findByPlanningSessionId(planningSessionId);

        return new PlanningRoomSessionDTO(planningSession.getTitle(), members, userStories);
    }
}
