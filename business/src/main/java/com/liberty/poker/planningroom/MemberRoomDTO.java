package com.liberty.poker.planningroom;

import com.liberty.poker.userstory.UserStory;
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
    private final List<UserStory> userStoryList;
    private final UUID planningSessionId;

    public MemberRoomDTO(final UUID id,
                         final String nickName,
                         final List<UserStory> userStoryList,
                         final UUID planningSessionId) {
        this.id = id;
        this.nickName = nickName;
        this.userStoryList = userStoryList;
        this.planningSessionId = planningSessionId;
    }
}
