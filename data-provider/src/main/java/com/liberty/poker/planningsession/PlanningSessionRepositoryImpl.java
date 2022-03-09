package com.liberty.poker.planningsession;

import com.liberty.poker.ObjectDomainNotFoundException;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class PlanningSessionRepositoryImpl implements PlanningSessionRepository {

    private final ConversionService conversionService;
    private final PlanningSessionEntityRepository planningSessionEntityRepository;

    public PlanningSessionRepositoryImpl(final ConversionService conversionService,
                                         final PlanningSessionEntityRepository planningSessionEntityRepository) {
        this.conversionService = conversionService;
        this.planningSessionEntityRepository = planningSessionEntityRepository;

    }

    @Override
    public PlanningSession save(final PlanningSession planningSession) {
        final var planningSessionEntity = conversionService.convert(planningSession, PlanningSessionEntity.class);
        return conversionService.convert(planningSessionEntityRepository.save(planningSessionEntity), PlanningSession.class);
    }

    @Override
    public Optional<PlanningSession> findById(final UUID id) {
        return planningSessionEntityRepository.findById(id)
                .map(planningSessionEntity -> conversionService.convert(planningSessionEntity, PlanningSession.class));
    }

    @Override
    public PlanningSession findMandatoryById(final UUID id) {
        return this.findById(id)
                .orElseThrow(() -> new ObjectDomainNotFoundException(String.format("Planning Session %s Not Found", id)));
    }

    @Override
    public List<PlanningSession> findAll() {
        return planningSessionEntityRepository.findAll()
                .stream()
                .map(planningSessionEntity -> conversionService.convert(planningSessionEntity, PlanningSession.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(final UUID id) {
        planningSessionEntityRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        planningSessionEntityRepository.deleteAll();
    }
}
