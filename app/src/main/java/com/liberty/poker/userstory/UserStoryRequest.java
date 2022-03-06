package com.liberty.poker.userstory;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter
@ToString
public class UserStoryRequest {
    private final String description;
    private final UUID planningSessionId;

    @JsonCreator
    public UserStoryRequest(@JsonProperty("description") final String description,
                            @JsonProperty("planningSessionId") final UUID planningSessionId) {
        this.description = description;
        this.planningSessionId = planningSessionId;
    }
}
