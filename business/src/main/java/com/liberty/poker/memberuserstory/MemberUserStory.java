package com.liberty.poker.memberuserstory;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter
@ToString
@EqualsAndHashCode
public class MemberUserStory {
    private final UUID memberId;
    private final UUID userStoryId;
    private final UUID planningSessionId;
    private final long voteValue;

    public MemberUserStory(final UUID memberId,
                           final UUID userStoryId,
                           final UUID planningSessionId) {
        this.memberId = memberId;
        this.userStoryId = userStoryId;
        this.planningSessionId = planningSessionId;
        this.voteValue = 0;
    }

    public MemberUserStory(final UUID memberId,
                           final UUID userStoryId,
                           final UUID planningSessionId,
                           final long voteValue) {
        this.memberId = memberId;
        this.userStoryId = userStoryId;
        this.planningSessionId = planningSessionId;
        this.voteValue = voteValue;
    }
}
