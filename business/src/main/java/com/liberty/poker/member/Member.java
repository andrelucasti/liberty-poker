package com.liberty.poker.member;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter
@ToString
@EqualsAndHashCode
public class Member {

    private final String nickName;
    private final UUID planningSessionId;

    public Member(final String nickName,
                  final UUID planningSessionId) {

        this.nickName = nickName;
        this.planningSessionId = planningSessionId;
    }
}
