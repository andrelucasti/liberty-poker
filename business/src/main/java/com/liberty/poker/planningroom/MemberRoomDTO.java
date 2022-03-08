package com.liberty.poker.planningroom;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@Getter
@ToString
@EqualsAndHashCode
public class MemberRoomDTO {
    private final UUID id;
    private final String nickName;
    private final List<UUID> storiesVoted;
    private final UUID planningSessionId;

    public MemberRoomDTO(final UUID id,
                         final String nickName,
                         final List<UUID> storiesVoted,
                         final UUID planningSessionId) {
        this.id = id;
        this.nickName = nickName;
        this.storiesVoted = storiesVoted;
        this.planningSessionId = planningSessionId;
    }
}
