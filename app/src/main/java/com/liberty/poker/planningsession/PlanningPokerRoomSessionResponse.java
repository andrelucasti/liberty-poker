package com.liberty.poker.planningsession;

import com.liberty.poker.member.Member;
import lombok.Getter;

import java.util.List;

@Getter
public class PlanningPokerRoomSessionResponse {

    private final String title;
    private final List<Member> members;

    public PlanningPokerRoomSessionResponse(final String title,
                                            final List<Member> members) {
        this.title = title;
        this.members = members;
    }
}
