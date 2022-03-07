package com.liberty.poker.member;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter
@ToString
@EqualsAndHashCode
public class Member {

    private UUID id;
    private final String nickName;
    private final UUID planningSessionId;

    public Member(final String nickName,
                  final UUID planningSessionId) {

        this.nickName = nickName;
        this.planningSessionId = planningSessionId;
    }

    public Member(final UUID id,
                  final String nickName,
                  final UUID planningSessionId) {
        this.id = id;
        this.nickName = nickName;
        this.planningSessionId = planningSessionId;
    }
}
