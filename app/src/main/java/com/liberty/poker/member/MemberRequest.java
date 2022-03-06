package com.liberty.poker.member;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.UUID;


@Getter
public class MemberRequest {

    private final String nickName;

    @JsonCreator
    public MemberRequest(@JsonProperty("nickName") final String nickName) {
        this.nickName = nickName;
    }

    @Getter
    public static class MemberRequestWrapper {

        private final MemberRequest memberRequest;
        private final UUID planningPokerSessionId;

        public MemberRequestWrapper(final MemberRequest memberRequest, final UUID planningPokerSessionId) {
            this.memberRequest = memberRequest;
            this.planningPokerSessionId = planningPokerSessionId;
        }
    }
}
