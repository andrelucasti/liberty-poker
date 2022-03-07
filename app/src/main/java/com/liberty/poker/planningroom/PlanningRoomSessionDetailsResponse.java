package com.liberty.poker.planningroom;

import com.liberty.poker.userstory.UserStory;
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
    private final List<UserStory> userStories;

    public PlanningRoomSessionDetailsResponse(final String title,
                                       final List<MemberRoomDTO> members,
                                       final List<UserStory> userStories) {
        this.title = title;
        this.members = members;
        this.userStories = userStories;
    }
}
