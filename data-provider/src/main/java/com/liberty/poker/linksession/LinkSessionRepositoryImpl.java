package com.liberty.poker.linksession;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class LinkSessionRepositoryImpl implements LinkSessionRepository {
    private final LinkSessionEntityRepository linkSessionEntityRepository;
    private final LinkSessionEntityToLinkSessionConverter entityToModel;
    private final LinkSessionToLinkSessionEntityConverter modelToEntity;

    public LinkSessionRepositoryImpl(final LinkSessionEntityRepository linkSessionEntityRepository,
                                     final LinkSessionEntityToLinkSessionConverter entityToModel,
                                     final LinkSessionToLinkSessionEntityConverter modelToEntity) {
        this.linkSessionEntityRepository = linkSessionEntityRepository;
        this.entityToModel = entityToModel;
        this.modelToEntity = modelToEntity;
    }

    @Override
    public LinkSession save(final LinkSession linkSession) {
        final var linkSessionEntity = modelToEntity.converter(linkSession);
        return entityToModel.converter(linkSessionEntityRepository.save(linkSessionEntity));
    }

    @Override
    public List<LinkSession> findAll() {
        return linkSessionEntityRepository.findAll()
                .stream()
                .map(entityToModel::converter)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteBy(final UUID planningSessionId) {
        linkSessionEntityRepository.deleteByPlanningSessionId(planningSessionId);
    }

    @Override
    public void deleteAll() {
        linkSessionEntityRepository.deleteAll();
    }

    @Override
    public Optional<LinkSession> findByPlanningSession(final UUID planningSessionId) {
        return linkSessionEntityRepository.findByPlanningSessionId(planningSessionId).map(entityToModel::converter);
    }
}
