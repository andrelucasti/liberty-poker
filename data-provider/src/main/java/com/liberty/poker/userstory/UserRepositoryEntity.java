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

//    @Modifying
//    @Query("update WorkerAttribute set value = :value where attributeKey = :attributeKey and value <> :value")
//    void redefineValueAllWorkers(@Param("attributeKey") AttributeKey attributeKey, @Param("value") String value);


    @Modifying
    @Query(value = "update USER_STORY set STATUS = :status where UUID = :id")
    void updateStatus(@Param("id") UUID id, @Param("status") String status);
}
