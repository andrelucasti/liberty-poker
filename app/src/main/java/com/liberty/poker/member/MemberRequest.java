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
        private final UUID planningSessionId;

        public MemberRequestWrapper(final MemberRequest memberRequest, final UUID planningSessionId) {
            this.memberRequest = memberRequest;
            this.planningSessionId = planningSessionId;
        }
    }
}
