package com.liberty.poker.userstory;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

import static com.liberty.poker.userstory.UserStory.UserStoryStatus;

@Getter
@ToString
@EqualsAndHashCode
public class UserStoryDTO {

    private final UUID id;
    private final String description;
    private final UserStoryStatus status;
    private final List<UUID> membersVoted;

    public UserStoryDTO(final UUID id,
                        final String description,
                        final UserStoryStatus status,
                        final List<UUID> membersVoted) {
        this.id = id;
        this.description = description;
        this.status = status;
        this.membersVoted = membersVoted;
    }
}
