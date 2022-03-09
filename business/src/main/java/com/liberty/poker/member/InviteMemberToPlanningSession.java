package com.liberty.poker.member;

import com.liberty.poker.ObjectDomainNotFoundException;
import com.liberty.poker.planningroom.PlanningRoomSessionDTO;
import com.liberty.poker.planningsession.PlanningSessionNotFoundException;
import com.liberty.poker.planningsession.PlanningSessionRepository;
import com.liberty.poker.userstory.UserStory;
import com.liberty.poker.userstory.UserStoryRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class InviteMemberToPlanningSession {

    private final CreateMember createMember;
    private final MemberRepository memberRepository;
    private final PlanningSessionRepository planningSessionRepository;
    private final UserStoryRepository userStoryRepository;


    public InviteMemberToPlanningSession(final CreateMember createMember,
                                         final MemberRepository memberRepository,
                                         final PlanningSessionRepository planningSessionRepository,
                                         final UserStoryRepository userStoryRepository) {
        this.memberRepository = memberRepository;
        this.planningSessionRepository = planningSessionRepository;
        this.createMember = createMember;
        this.userStoryRepository = userStoryRepository;
    }

    public PlanningRoomSessionDTO execute(final Member member) throws PlanningSessionNotFoundException {
        final var planningSession = planningSessionRepository.findMandatoryById(member.getPlanningSessionId());

        createMember.execute(member);
        final var members = memberRepository.findByPlanningSessionId(planningSession.getId());
        final var userStories = userStoryRepository.findByPlanningSessionId(planningSession.getId());

        return new PlanningRoomSessionDTO(planningSession.getTitle(), members, userStories);
    }
}
