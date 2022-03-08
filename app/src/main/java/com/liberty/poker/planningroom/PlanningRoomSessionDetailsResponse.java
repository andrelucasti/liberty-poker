package com.liberty.poker.planningroom;

import com.liberty.poker.userstory.UserStoryDTO;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@EqualsAndHashCode
@ToString
public class PlanningRoomSessionDetailsResponse {

    private final String title;
    private final List<MemberRoomDTO> members;
    private final List<UserStoryDTO> stories;

    public PlanningRoomSessionDetailsResponse(final String title,
                                              final List<MemberRoomDTO> members,
                                              final List<UserStoryDTO> stories) {
        this.title = title;
        this.members = members;
        this.stories = stories;
    }
}
