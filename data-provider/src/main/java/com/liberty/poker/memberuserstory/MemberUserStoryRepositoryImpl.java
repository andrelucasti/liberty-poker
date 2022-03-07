package com.liberty.poker.memberuserstory;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class MemberUserStoryRepositoryImpl implements MemberUserStoryRepository{

    private final MemberUserStoryToMemberStoryEntityConverter modelToEntity;
    private final MemberUserStoryEntityToMemberStoryConverter entityToModel;
    private final MemberUserStoryRepositoryEntity memberUserStoryRepositoryEntity;

    public MemberUserStoryRepositoryImpl(final MemberUserStoryToMemberStoryEntityConverter modelToEntity,
                                         final MemberUserStoryEntityToMemberStoryConverter entityToModel,
                                         final MemberUserStoryRepositoryEntity memberUserStoryRepositoryEntity) {
        this.modelToEntity = modelToEntity;
        this.entityToModel = entityToModel;
        this.memberUserStoryRepositoryEntity = memberUserStoryRepositoryEntity;
    }

    @Override
    public MemberUserStory save(final MemberUserStory memberUserStory) {
        final var memberUserStoryEntity = modelToEntity.convert(memberUserStory);
        return entityToModel.convert(memberUserStoryRepositoryEntity.save(memberUserStoryEntity));
    }

    @Override
    public List<MemberUserStory> findAll() {
        return memberUserStoryRepositoryEntity.findAll()
                .stream()
                .map(entityToModel::convert)
                .collect(Collectors.toList());
    }

    @Override
    public List<MemberUserStory> findByMemberIdAndPlanningSessionId(final UUID id, final UUID planningSessionId) {
        return memberUserStoryRepositoryEntity.findByMemberIdAndPlanningSessionId(id, planningSessionId)
                .stream()
                .map(entityToModel::convert)
                .collect(Collectors.toList());
    }
}
