package com.liberty.poker.memberuserstory;

import org.springframework.stereotype.Component;

@Component
public class MemberUserStoryToMemberStoryEntityConverter {

    public MemberUserStoryEntity convert(final MemberUserStory source) {
        return MemberUserStoryEntity.of(
                source.getMemberId(),
                source.getUserStoryId(),
                source.getPlanningSessionId(),
                source.getVoteValue());
    }
}
