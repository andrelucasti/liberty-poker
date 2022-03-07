package com.liberty.poker.planningroom;

import com.liberty.poker.userstory.UserStory;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@EqualsAndHashCode
public class PlanningRoomSessionDetailsDTO {

    private final String title;
    private final List<UserStory> userStoryList;
    private final List<MemberRoomDTO> memberRoomDTOS;


    public PlanningRoomSessionDetailsDTO(final String title,
                                  final List<MemberRoomDTO> memberRoomDTOS,
                                  final List<UserStory> userStories) {
        this.title = title;
        this.memberRoomDTOS = memberRoomDTOS;
        this.userStoryList = userStories;
    }
}
