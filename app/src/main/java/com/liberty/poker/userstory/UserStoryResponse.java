package com.liberty.poker.userstory;

import lombok.Getter;

import java.util.UUID;

@Getter
public class UserStoryResponse {
    private final String description;
    private final UUID planningSessionId;

    public UserStoryResponse(final String description,
                             final UUID planningSessionId) {
        this.description = description;
        this.planningSessionId = planningSessionId;
    }
}
