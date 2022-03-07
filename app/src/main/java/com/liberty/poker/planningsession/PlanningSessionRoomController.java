package com.liberty.poker.planningsession;

import com.liberty.poker.member.InviteMemberToPlanningSession;
import com.liberty.poker.member.Member;
import com.liberty.poker.member.MemberRequest;
import com.liberty.poker.planningroom.DetailsPlanningRoomSession;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(value = "/room")
public class PlanningSessionRoomController {

    private final ConversionService conversionService;
    private final InviteMemberToPlanningSession inviteMemberToPlanningSession;
    private final DetailsPlanningRoomSession detailsPlanningRoomSession;

    public PlanningSessionRoomController(final ConversionService conversionService,
                                         final InviteMemberToPlanningSession inviteMemberToPlanningSession,
                                         final DetailsPlanningRoomSession detailsPlanningRoomSession) {
        this.conversionService = conversionService;
        this.inviteMemberToPlanningSession = inviteMemberToPlanningSession;
        this.detailsPlanningRoomSession = detailsPlanningRoomSession;
    }

    @PostMapping("{planningSessionId}")
    //TODO change to new Controller and create new test when the room not exist anymore
    public ResponseEntity<PlanningRoomSessionResponse> joinToRoom(@PathVariable final UUID planningSessionId,
                                                                  @RequestBody final MemberRequest memberRequest) throws PlanningSessionNotFoundException {

        final var member = conversionService.convert(new MemberRequest.MemberRequestWrapper(memberRequest, planningSessionId),
                Member.class);

        //TODO create ExceptionControllerAdvice
        final var planningPokerRoomSessionDTO = inviteMemberToPlanningSession.execute(member);

        return ResponseEntity.status(HttpStatus.CREATED).body(conversionService.convert(planningPokerRoomSessionDTO, PlanningRoomSessionResponse.class));
    }

    @GetMapping("{planningSessionId}")
    //TODO create a new test when the room not exist anymore
    public ResponseEntity<PlanningRoomSessionResponse> showRoom(@PathVariable final UUID planningSessionId){

        final var planningRoomSessionDTO = detailsPlanningRoomSession.execute(planningSessionId);

        return ResponseEntity.status(HttpStatus.OK).body(conversionService.convert(planningRoomSessionDTO, PlanningRoomSessionResponse.class));
    }
}
