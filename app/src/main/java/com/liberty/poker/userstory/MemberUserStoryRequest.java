package com.liberty.poker.userstory;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter
@ToString
public class MemberUserStoryRequest {

    private final UUID memberId;
    private final UUID userStoryId;
    private final long value;

    @JsonCreator
    public MemberUserStoryRequest(@JsonProperty("memberId")  final UUID memberId,
                                  @JsonProperty("userStoryId") final UUID userStoryId,
                                  @JsonProperty("value") final long value) {
        this.memberId = memberId;
        this.userStoryId = userStoryId;
        this.value = value;
    }
}
