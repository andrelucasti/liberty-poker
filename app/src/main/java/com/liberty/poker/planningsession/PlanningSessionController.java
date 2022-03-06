package com.liberty.poker.planningsession;


import com.liberty.poker.member.JoinMember;
import com.liberty.poker.member.MemberRequest;
import com.liberty.poker.member.Member;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(path = "/planning-session")
public class PlanningSessionController {

    private final CreatePlanningPokerSession createPlanningPokerSession;
    private final PlanningSessionRequestToPlanningSessionConverter requestToModel;
    private final PlanningPokerSessionDTOToPlanningSessionResponseConverter dtoToResponse;

    private final ConversionService converter;
    private final JoinMember joinMember;

    public PlanningSessionController(final CreatePlanningPokerSession createPlanningPokerSession,
                                     final PlanningSessionRequestToPlanningSessionConverter requestToModel,
                                     final PlanningPokerSessionDTOToPlanningSessionResponseConverter dtoToResponse,
                                     final ConversionService converter,
                                     final JoinMember joinMember) {
        this.createPlanningPokerSession = createPlanningPokerSession;
        this.requestToModel = requestToModel;
        this.dtoToResponse = dtoToResponse;
        this.converter = converter;
        this.joinMember = joinMember;
    }

    @PostMapping
    public ResponseEntity<PlanningSessionResponse> create(@RequestBody final PlanningSessionRequest planningSessionRequest){

        //Wrapper
        final var planningPokerSessionDTO =
                createPlanningPokerSession.execute(requestToModel.converter(planningSessionRequest));

        return ResponseEntity.status(HttpStatus.CREATED).body(dtoToResponse.converter(planningPokerSessionDTO));
    }

    @PostMapping
    @RequestMapping(path = "/room/{planningPokerSessionId}")
    public ResponseEntity<PlanningPokerRoomSessionResponse> joinToRoom(@PathVariable final UUID planningPokerSessionId,
                                                     @RequestBody final MemberRequest memberRequest) throws PlanningSessionNotFoundException {

        final var member = converter.convert(new MemberRequest.MemberRequestWrapper(memberRequest, planningPokerSessionId),
                Member.class);

        //TODO create ExceptionControllerAdvice
        final var planningPokerRoomSessionDTO = joinMember.execute(member);

        return ResponseEntity.status(HttpStatus.CREATED).body(converter.convert(planningPokerRoomSessionDTO, PlanningPokerRoomSessionResponse.class));
    }
}
