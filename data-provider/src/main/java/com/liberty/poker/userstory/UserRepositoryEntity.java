package com.liberty.poker.userstory;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UserRepositoryEntity extends JpaRepository<UserStoryEntity, UUID> {
    void deleteByPlanningSessionId(UUID planningSessionId);
    List<UserStoryEntity> findByPlanningSessionId(UUID planningSessionId);
}
