package com.liberty.poker.planningsession;

import com.liberty.poker.ObjectDomainNotFoundException;
import com.liberty.poker.linksession.LinkSessionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListAllPlanningSession {

    private final PlanningSessionRepository planningSessionRepository;
    private final LinkSessionRepository linkSessionRepository;

    public ListAllPlanningSession(final PlanningSessionRepository planningSessionRepository,
                                  final LinkSessionRepository linkSessionRepository) {
        this.planningSessionRepository = planningSessionRepository;
        this.linkSessionRepository = linkSessionRepository;
    }

    public List<PlanningSessionDTO> execute(){
        return planningSessionRepository.findAll()
                .stream()
                .map(this::createPlanningSessionDTO)
                .collect(Collectors.toList());
    }

    private PlanningSessionDTO createPlanningSessionDTO(final PlanningSession planningSession) {
        return new PlanningSessionDTO(planningSession, linkSessionRepository.findByPlanningSession(planningSession.getId())
                .orElseThrow(() -> new ObjectDomainNotFoundException(String.format("Not found link to PlanningSessionId %s", planningSession.getId()))));
    }
}
