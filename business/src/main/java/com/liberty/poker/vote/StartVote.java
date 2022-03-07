package com.liberty.poker.vote;

import com.liberty.poker.userstory.UserStory;
import com.liberty.poker.userstory.UserStoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.liberty.poker.userstory.UserStory.UserStoryStatus.*;

@Service
public class StartVote {
    private final UserStoryRepository userStoryRepository;

    public StartVote(final UserStoryRepository userStoryRepository) {
        this.userStoryRepository = userStoryRepository;
    }

    @Transactional
    public void execute(final UUID planningSessionId) {
        userStoryRepository.findByPlanningSessionId(planningSessionId)
                .stream()
                .filter(userStory -> userStory.getUserStoryStatus().equals(PENDING))
                .map(this::createUserStoryWithVotingStatus)
                .forEach(userStoryRepository::update);
    }

    private UserStory createUserStoryWithVotingStatus(final UserStory userStory) {
        return new UserStory(userStory.getId(), userStory.getDescription(), VOTING, userStory.getPlanningSessionId());
    }
}
