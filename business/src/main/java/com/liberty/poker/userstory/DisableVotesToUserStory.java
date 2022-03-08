package com.liberty.poker.userstory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.liberty.poker.userstory.UserStory.UserStoryStatus.VOTED;

@Service
public class DisableVotesToUserStory {

    private final UserStoryRepository userStoryRepository;

    public DisableVotesToUserStory(final UserStoryRepository userStoryRepository) {
        this.userStoryRepository = userStoryRepository;
    }

    @Transactional
    public void execute(final UUID planningSessionId) {

        final var userStories = userStoryRepository.findByPlanningSessionId(planningSessionId);

        userStories.stream()
                .map(this::createUserStoryVoted)
                .forEach(userStoryRepository::updateStatus);
    }

    private UserStory createUserStoryVoted(UserStory userStory) {
        return new UserStory(userStory.getId(), userStory.getDescription(), VOTED, userStory.getPlanningSessionId());
    }
}
