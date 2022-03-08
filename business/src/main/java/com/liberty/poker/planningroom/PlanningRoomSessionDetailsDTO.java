package com.liberty.poker.planningroom;

import com.liberty.poker.userstory.UserStory;
import com.liberty.poker.userstory.UserStoryDTO;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@EqualsAndHashCode
public class PlanningRoomSessionDetailsDTO {

    private final String title;
    private final List<UserStoryDTO> userStoriesDTOS;
    private final List<MemberRoomDTO> memberRoomDTOS;


    public PlanningRoomSessionDetailsDTO(final String title,
                                  final List<MemberRoomDTO> memberRoomDTOS,
                                  final List<UserStoryDTO> userStoriesDTOS) {
        this.title = title;
        this.memberRoomDTOS = memberRoomDTOS;
        this.userStoriesDTOS = userStoriesDTOS;
    }
}
