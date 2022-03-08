package com.liberty.poker.planningsession;


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
    private final CreatePlanningSession createPlanningSession;
    private final DestroyPlanningSession destroyPlanningSession;

    public PlanningSessionController(final ConversionService converter,
                                     final CreatePlanningSession createPlanningSession,
                                     final DestroyPlanningSession destroyPlanningSession) {
        this.converter = converter;
        this.createPlanningSession = createPlanningSession;
        this.destroyPlanningSession = destroyPlanningSession;
    }

    @PostMapping
    public ResponseEntity<PlanningSessionResponse> create(@RequestBody final PlanningSessionRequest planningSessionRequest){

        //Wrapper
        final var planningPokerSessionDTO =
                createPlanningSession.execute(converter.convert(planningSessionRequest, PlanningSession.class));

        return ResponseEntity.status(HttpStatus.CREATED).body(converter.convert(planningPokerSessionDTO, PlanningSessionResponse.class));
    }

    @DeleteMapping("/{planningSessionId}")
    public ResponseEntity<HttpStatus> destroy(@PathVariable final UUID planningSessionId){

        destroyPlanningSession.execute(planningSessionId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
