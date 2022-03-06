package com.liberty.poker.userstory;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter
@ToString
@EqualsAndHashCode
public class UserStory {
    private UUID id;
    private final String description;
    private final UUID planningSessionId;

    public UserStory(final String description,
                     final UUID planningSessionId) {
        this.description = description;
        this.planningSessionId = planningSessionId;
    }

    public UserStory(final UUID id,
                     final String description,
                     final UUID planningSessionId) {
        this.id = id;
        this.description = description;
        this.planningSessionId = planningSessionId;
    }
}
