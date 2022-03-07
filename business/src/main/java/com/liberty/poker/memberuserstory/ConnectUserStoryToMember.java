package com.liberty.poker.memberuserstory;

import com.liberty.poker.userstory.UserStory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ConnectUserStoryToMember {
    private final MemberUserStoryRepository memberUserStoryRepository;

    public ConnectUserStoryToMember(final MemberUserStoryRepository memberUserStoryRepository) {
        this.memberUserStoryRepository = memberUserStoryRepository;
    }

    public void execute(final UUID memberId,
                        final List<UserStory> userStories,
                        final UUID planningSessionId) {


        userStories.stream()
                .map(userStory -> new MemberUserStory(memberId, userStory.getId(), planningSessionId))
                .forEach(memberUserStoryRepository::save);
    }
}
