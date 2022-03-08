package com.liberty.poker.planningroom;

import com.liberty.poker.userstory.UserStory;
import com.liberty.poker.userstory.UserStory.UserStoryStatus;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@Getter
@ToString
@EqualsAndHashCode
public class MemberRoomDTO {
    private final UUID id;
    private final String nickName;
    private final List<StoryVotedDTO> storiesVoted;
    private final UUID planningSessionId;

    public MemberRoomDTO(final UUID id,
                         final String nickName,
                         final List<StoryVotedDTO> storiesVoted,
                         final UUID planningSessionId) {
        this.id = id;
        this.nickName = nickName;
        this.storiesVoted = storiesVoted;
        this.planningSessionId = planningSessionId;
    }

    @Getter
    @ToString
    @EqualsAndHashCode
    public static class StoryVotedDTO{

        private final UUID id;
        private final String description;
        private final UserStoryStatus userStoryStatus;
        private final Long voteValue;

        public StoryVotedDTO(final UUID id,
                             final String description,
                             final UserStoryStatus userStoryStatus,
                             final Long voteValue) {
            this.id = id;
            this.description = description;
            this.userStoryStatus = userStoryStatus;
            this.voteValue = voteValue;
        }

        public Long getVoteValue() {
            if(userStoryStatus.equals(UserStoryStatus.VOTED)){
                return voteValue;
            }
            return null;
        }
    }
}
