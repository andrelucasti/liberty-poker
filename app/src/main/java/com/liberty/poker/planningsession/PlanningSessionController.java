package com.liberty.poker.planningsession;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/planning-session")
public class PlanningSessionController {

    private final CreatePlanningPokerSession createPlanningPokerSession;
    private final PlanningSessionRequestToPlanningSessionConverter requestToModel;
    private final PlanningPokerSessionDTOToPlanningSessionResponseConverter dtoToResponse;

    public PlanningSessionController(final CreatePlanningPokerSession createPlanningPokerSession,
                                     final PlanningSessionRequestToPlanningSessionConverter requestToModel,
                                     final PlanningPokerSessionDTOToPlanningSessionResponseConverter dtoToResponse) {
        this.createPlanningPokerSession = createPlanningPokerSession;
        this.requestToModel = requestToModel;
        this.dtoToResponse = dtoToResponse;
    }

    @PostMapping
    public ResponseEntity<PlanningSessionResponse> create(@RequestBody final PlanningSessionRequest planningSessionRequest){

        final var planningPokerSessionDTO =
                createPlanningPokerSession.execute(requestToModel.converter(planningSessionRequest));

        return ResponseEntity.status(HttpStatus.CREATED).body(dtoToResponse.converter(planningPokerSessionDTO));
    }
}
