package com.liberty.poker.userstory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

import static com.liberty.poker.userstory.UserStory.*;

public interface UserRepositoryEntity extends JpaRepository<UserStoryEntity, UUID> {
    void deleteByPlanningSessionId(UUID planningSessionId);
    List<UserStoryEntity> findByPlanningSessionId(UUID planningSessionId);

    @Modifying
    @Query(value = "update USER_STORY u set u.version=u.version+1, u.status = :status where u.uuid = :id")
    void updateStatus(@Param("id") UUID id, @Param("status") String status);
}
