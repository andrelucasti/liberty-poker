package com.liberty.poker.userstory;

import com.google.common.annotations.VisibleForTesting;
import com.liberty.poker.ObjectDomainNotFoundException;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class UserStoryImpl implements UserStoryRepository{

    private final ConversionService conversionService;
    private final UserRepositoryEntity userRepositoryEntity;

    public UserStoryImpl(final ConversionService conversionService,
                         final UserRepositoryEntity userRepositoryEntity) {
        this.conversionService = conversionService;
        this.userRepositoryEntity = userRepositoryEntity;
    }

    @Override
    public UserStory save(final UserStory userStory) {
        final var convert = conversionService.convert(userStory, UserStoryEntity.class);

        final var userStoryEntity =
                userRepositoryEntity.save(Objects.requireNonNull(convert));
        return conversionService.convert(userStoryEntity, UserStory.class);
    }

    @Override
    public List<UserStory> findAll() {
        return userRepositoryEntity.findAll()
                .stream()
                .map(userStoryEntity -> conversionService.convert(userStoryEntity, UserStory.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(final UUID id) {
        userRepositoryEntity.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteByPlanningSessionId(final UUID planningSessionId) {
        userRepositoryEntity.deleteByPlanningSessionId(planningSessionId);
    }

    @Override
    public List<UserStory> findByPlanningSessionId(final UUID planningSessionId) {
        return userRepositoryEntity.findByPlanningSessionId(planningSessionId).stream()
                .map(userStoryEntity -> conversionService.convert(userStoryEntity, UserStory.class))
                .collect(Collectors.toList());
    }

    @Override
    @VisibleForTesting
    public void deleteAll() {
        userRepositoryEntity.deleteAll();
    }

    @Override
    public void updateStatus(final UserStory userStory) {
        userRepositoryEntity.updateStatus(userStory.getId(), userStory.getUserStoryStatus().toString());
    }

    @Override
    public Optional<UserStory> findById(final UUID id) {
        return userRepositoryEntity.findById(id).map(userStoryEntity -> conversionService.convert(userStoryEntity, UserStory.class));
    }

    @Override
    public UserStory findMandatoryById(final UUID id) {
        return this.findById(id).orElseThrow(() -> new ObjectDomainNotFoundException(String.format("User Story not found, %s", id)));
    }
}
