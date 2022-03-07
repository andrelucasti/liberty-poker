package com.liberty.poker.userstory;

import lombok.Getter;

import java.util.UUID;

@Getter
public class UserStoryResponse {
    private final UUID id;
    private final String description;
    private final UserStory.UserStoryStatus status;
    private final UUID planningSessionId;

    public UserStoryResponse(final UUID id,
                             final String description,
                             final UserStory.UserStoryStatus status,
                             final UUID planningSessionId) {
        this.id = id;
        this.description = description;
        this.status = status;
        this.planningSessionId = planningSessionId;
    }
}
