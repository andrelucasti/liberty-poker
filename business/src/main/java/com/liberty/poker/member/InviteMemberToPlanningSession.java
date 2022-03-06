package com.liberty.poker.member;

import com.liberty.poker.planningroom.PlanningRoomSessionDTO;
import com.liberty.poker.planningsession.PlanningSessionNotFoundException;
import com.liberty.poker.planningsession.PlanningSessionRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class InviteMemberToPlanningSession {

    private final CreateMember createMember;
    private final MemberRepository memberRepository;
    private final PlanningSessionRepository planningSessionRepository;


    public InviteMemberToPlanningSession(final CreateMember createMember,
                                         final MemberRepository memberRepository,
                                         final PlanningSessionRepository planningSessionRepository) {
        this.memberRepository = memberRepository;
        this.planningSessionRepository = planningSessionRepository;
        this.createMember = createMember;
    }

    public PlanningRoomSessionDTO execute(final Member member) throws PlanningSessionNotFoundException {
        createMember.execute(member);
        final var planningSession = planningSessionRepository.findById(member.getPlanningSessionId())
                .orElseThrow(()-> new PlanningSessionNotFoundException(String.format("PlanningSession %s not found", member.getPlanningSessionId())));

        final var members = memberRepository.findByPlanningSessionId(planningSession.getId());

        //TODO storyList
        return new PlanningRoomSessionDTO(planningSession.getTitle(), members, Collections.emptyList());
    }
}
