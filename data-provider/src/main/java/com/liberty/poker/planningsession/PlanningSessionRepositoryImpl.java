package com.liberty.poker.planningsession;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class PlanningSessionRepositoryImpl implements PlanningSessionRepository {

    private final PlanningSessionEntityRepository planningSessionEntityRepository;
    private final PlanningSessionToPlanningSessionEntityConverter modelToEntity;
    private final PlanningSessionEntityToPlanningSessionConverter entityToModel;

    public PlanningSessionRepositoryImpl(final PlanningSessionEntityRepository planningSessionEntityRepository,
                                         final PlanningSessionToPlanningSessionEntityConverter modelToEntity,
                                         final PlanningSessionEntityToPlanningSessionConverter entityToModel) {
        this.planningSessionEntityRepository = planningSessionEntityRepository;
        this.modelToEntity = modelToEntity;
        this.entityToModel = entityToModel;
    }

    @Override
    public PlanningSession save(final PlanningSession planningSession) {
        final var planningSessionEntity = modelToEntity.converter(planningSession);
        return entityToModel.converter(planningSessionEntityRepository.save(planningSessionEntity));
    }

    @Override
    public Optional<PlanningSession> findById(final UUID id) {
        return planningSessionEntityRepository.findById(id).map(entityToModel::converter);
    }

    @Override
    public PlanningSession findMandatoryById(final UUID id) {

        return planningSessionEntityRepository.findById(id).map(entityToModel::converter)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Planning Session %s Not Found", id)));
    }

    @Override
    public List<PlanningSession> findAll() {
        return planningSessionEntityRepository.findAll()
                .stream()
                .map(entityToModel::converter)
                .collect(Collectors.toList());
    }
}
