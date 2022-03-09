package com.liberty.poker.planningsession;


import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/planning-session")
public class PlanningSessionController {

    private final ConversionService converter;
    private final CreatePlanningSession createPlanningSession;
    private final DestroyPlanningSession destroyPlanningSession;
    private final ListAllPlanningSession listAllPlanningSession;

    public PlanningSessionController(final ConversionService converter,
                                     final CreatePlanningSession createPlanningSession,
                                     final DestroyPlanningSession destroyPlanningSession,
                                     final ListAllPlanningSession listAllPlanningSession) {
        this.converter = converter;
        this.createPlanningSession = createPlanningSession;
        this.destroyPlanningSession = destroyPlanningSession;
        this.listAllPlanningSession = listAllPlanningSession;
    }

    @PostMapping
    public ResponseEntity<PlanningSessionResponse> create(@RequestBody final PlanningSessionRequest planningSessionRequest){

        //Wrapper
        final var planningSessionDTO =
                createPlanningSession.execute(converter.convert(planningSessionRequest, PlanningSession.class));

        return ResponseEntity.status(HttpStatus.CREATED).body(converter.convert(planningSessionDTO, PlanningSessionResponse.class));
    }

    @GetMapping
    public ResponseEntity<List<PlanningSessionResponse>> list(){
        final var planningSessionResponses = listAllPlanningSession.execute()
                .stream()
                .map(planningSessionDTO -> converter.convert(planningSessionDTO, PlanningSessionResponse.class))
                .collect(Collectors.toList());

        if(planningSessionResponses.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(planningSessionResponses);
        }

        return ResponseEntity.status(HttpStatus.OK).body(planningSessionResponses);
    }

    @DeleteMapping("/{planningSessionId}")
    public ResponseEntity<HttpStatus> destroy(@PathVariable final UUID planningSessionId){

        destroyPlanningSession.execute(planningSessionId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
