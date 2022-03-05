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

    @PostMapping
    public ResponseEntity<PlanningSessionRequest> create(@RequestBody final PlanningSessionRequest planningSessionRequest){

        System.out.println(planningSessionRequest.toString());

        return ResponseEntity.status(HttpStatus.CREATED).body(planningSessionRequest);
    }
}
