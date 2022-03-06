package com.liberty.poker.member;

import com.liberty.poker.planningsession.PlanningPokerRoomSessionDTO;
import com.liberty.poker.planningsession.PlanningSessionNotFoundException;
import com.liberty.poker.planningsession.PlanningSessionRepository;
import org.springframework.stereotype.Service;

@Service
public class JoinMember {

    private final CreateMember createMember;
    private final MemberRepository memberRepository;
    private final PlanningSessionRepository planningSessionRepository;


    public JoinMember(final CreateMember createMember,
                      final MemberRepository memberRepository,
                      final PlanningSessionRepository planningSessionRepository) {
        this.memberRepository = memberRepository;
        this.planningSessionRepository = planningSessionRepository;
        this.createMember = createMember;
    }

    public PlanningPokerRoomSessionDTO execute(final Member member) throws PlanningSessionNotFoundException {
        createMember.execute(member);
        final var planningSession = planningSessionRepository.findById(member.getPlanningSessionId())
                .orElseThrow(()-> new PlanningSessionNotFoundException(String.format("PlanningSession %s not found", member.getPlanningSessionId())));

        final var members = memberRepository.findMembersBy(planningSession.getId());

        return new PlanningPokerRoomSessionDTO(planningSession.getTitle(), members);
    }
}
