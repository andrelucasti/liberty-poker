package com.liberty.poker.planningsession;


import com.liberty.poker.member.InviteMemberToPlanningSession;
import com.liberty.poker.member.MemberRequest;
import com.liberty.poker.member.Member;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(path = "/planning-session")
public class PlanningSessionController {

    private final ConversionService converter;
    private final CreatePlanningPokerSession createPlanningPokerSession;
    private final InviteMemberToPlanningSession inviteMemberToPlanningSession;
    private final DestroyPlanningPokerSession destroyPlanningPokerSession;

    public PlanningSessionController(final ConversionService converter,
                                     final CreatePlanningPokerSession createPlanningPokerSession,
                                     final InviteMemberToPlanningSession inviteMemberToPlanningSession,
                                     final DestroyPlanningPokerSession destroyPlanningPokerSession) {
        this.converter = converter;
        this.createPlanningPokerSession = createPlanningPokerSession;
        this.inviteMemberToPlanningSession = inviteMemberToPlanningSession;
        this.destroyPlanningPokerSession = destroyPlanningPokerSession;
    }

    @PostMapping
    public ResponseEntity<PlanningSessionResponse> create(@RequestBody final PlanningSessionRequest planningSessionRequest){

        //Wrapper
        final var planningPokerSessionDTO =
                createPlanningPokerSession.execute(converter.convert(planningSessionRequest, PlanningSession.class));

        return ResponseEntity.status(HttpStatus.CREATED).body(converter.convert(planningPokerSessionDTO, PlanningSessionResponse.class));
    }

    @PostMapping("/room/{planningSessionId}")
    //TODO change to new Controller and create new test when the room not exist anymore
    public ResponseEntity<PlanningPokerRoomSessionResponse> joinToRoom(@PathVariable final UUID planningSessionId,
                                                     @RequestBody final MemberRequest memberRequest) throws PlanningSessionNotFoundException {

        final var member = converter.convert(new MemberRequest.MemberRequestWrapper(memberRequest, planningSessionId),
                Member.class);

        //TODO create ExceptionControllerAdvice
        final var planningPokerRoomSessionDTO = inviteMemberToPlanningSession.execute(member);

        return ResponseEntity.status(HttpStatus.CREATED).body(converter.convert(planningPokerRoomSessionDTO, PlanningPokerRoomSessionResponse.class));
    }
    @DeleteMapping("/{planningSessionId}")
    public ResponseEntity<HttpStatus> destroy(@PathVariable final UUID planningSessionId){

        destroyPlanningPokerSession.execute(planningSessionId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
