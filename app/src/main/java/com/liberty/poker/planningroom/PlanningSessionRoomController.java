package com.liberty.poker.planningroom;

import com.liberty.poker.member.InviteMemberToPlanningSession;
import com.liberty.poker.member.Member;
import com.liberty.poker.member.MemberRequest;
import com.liberty.poker.planningsession.PlanningSessionNotFoundException;
import com.liberty.poker.userstory.DisableVotesToUserStory;
import com.liberty.poker.userstory.EnableVotesToUserStory;
import com.liberty.poker.userstory.MemberUserStoryRequest;
import com.liberty.poker.userstory.UserStoryVoteException;
import com.liberty.poker.userstory.VoteForUserStory;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    private final EnableVotesToUserStory enableVotesToUserStory;
    private final VoteForUserStory voteForUserStory;
    private final DisableVotesToUserStory disableVotesToUserStory;

    public PlanningSessionRoomController(final ConversionService conversionService,
                                         final InviteMemberToPlanningSession inviteMemberToPlanningSession,
                                         final DetailsPlanningRoomSession detailsPlanningRoomSession,
                                         final EnableVotesToUserStory enableVotesToUserStory,
                                         final VoteForUserStory voteForUserStory,
                                         final DisableVotesToUserStory disableVotesToUserStory) {
        this.conversionService = conversionService;
        this.inviteMemberToPlanningSession = inviteMemberToPlanningSession;
        this.detailsPlanningRoomSession = detailsPlanningRoomSession;
        this.enableVotesToUserStory = enableVotesToUserStory;
        this.voteForUserStory = voteForUserStory;
        this.disableVotesToUserStory = disableVotesToUserStory;
    }

    @PostMapping("{planningSessionId}")
    public ResponseEntity<PlanningRoomSessionResponse> joinToRoom(@PathVariable final UUID planningSessionId,
                                                                  @RequestBody final MemberRequest memberRequest) throws PlanningSessionNotFoundException {

        final var member = conversionService.convert(new MemberRequest.MemberRequestWrapper(memberRequest, planningSessionId),
                Member.class);

        final var planningRoomSessionDTO = inviteMemberToPlanningSession.execute(member);

        return ResponseEntity.status(HttpStatus.CREATED).body(conversionService.convert(planningRoomSessionDTO, PlanningRoomSessionResponse.class));
    }

    @GetMapping("{planningSessionId}")
    public ResponseEntity<PlanningRoomSessionDetailsResponse> details(@PathVariable final UUID planningSessionId){

        final var planningRoomSessionDetailsDTO = detailsPlanningRoomSession.execute(planningSessionId);
        final var planningRoomSessionDetailsResponse = conversionService.convert(planningRoomSessionDetailsDTO, PlanningRoomSessionDetailsResponse.class);
        return ResponseEntity.status(HttpStatus.OK).body(planningRoomSessionDetailsResponse);
    }

    @PutMapping("{planningSessionId}/start")
    public ResponseEntity<HttpStatus> start(@PathVariable final UUID planningSessionId){

        enableVotesToUserStory.execute(planningSessionId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("{planningSessionId}/stop")
    public ResponseEntity<HttpStatus> stop(@PathVariable final UUID planningSessionId){

        disableVotesToUserStory.execute(planningSessionId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("{planningSessionId}/vote")
    public ResponseEntity<HttpStatus> vote(@PathVariable final UUID planningSessionId,
                                           @RequestBody final MemberUserStoryRequest memberUserStoryRequest ) throws UserStoryVoteException {

        voteForUserStory.execute(memberUserStoryRequest.getMemberId(), memberUserStoryRequest.getUserStoryId(), planningSessionId, memberUserStoryRequest.getValue());

        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
