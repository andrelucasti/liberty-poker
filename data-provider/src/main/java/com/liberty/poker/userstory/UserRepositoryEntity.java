package com.liberty.poker.userstory;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepositoryEntity extends JpaRepository<UserStoryEntity, UUID> {
    void deleteByPlanningSessionId(UUID planningSessionId);
}
