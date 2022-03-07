package com.liberty.poker.planningsession;

import com.liberty.poker.member.Member;
import com.liberty.poker.planningroom.MemberRoomDTO;
import com.liberty.poker.userstory.UserStory;
import lombok.Getter;

import java.util.List;

@Getter
public class PlanningRoomSessionResponse {

    private final String title;
    private final List<Member> members;
    private final List<MemberRoomDTO> memberRoomDTOS;
    private final List<UserStory> userStories;

    public PlanningRoomSessionResponse(final String title,
                                       final List<Member> members,
                                       final List<MemberRoomDTO> memberRoomDTOS,
                                       final List<UserStory> userStories) {
        this.title = title;
        this.members = members;
        this.memberRoomDTOS = memberRoomDTOS;
        this.userStories = userStories;
    }
}
