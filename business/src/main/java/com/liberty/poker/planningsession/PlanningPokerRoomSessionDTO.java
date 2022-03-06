package com.liberty.poker.planningsession;

import com.liberty.poker.member.Member;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class PlanningPokerRoomSessionDTO {

    private final String title;
    private final List<Member> members;

    public PlanningPokerRoomSessionDTO(final String title, final List<Member> members) {
        this.title = title;
        this.members = members;
    }
}
