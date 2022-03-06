package com.liberty.poker.planningroom;

import com.liberty.poker.member.Member;
import com.liberty.poker.userstory.UserStory;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@EqualsAndHashCode
public class PlanningRoomSessionDTO {

    private final String title;
    private final List<Member> members;
    private final List<UserStory> userStoryList;

    public PlanningRoomSessionDTO(final String title,
                                  final List<Member> members,
                                  final List<UserStory> userStoryList) {
        this.title = title;
        this.members = members;
        this.userStoryList = userStoryList;
    }
}
