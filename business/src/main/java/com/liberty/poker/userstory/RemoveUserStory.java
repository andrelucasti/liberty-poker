package com.liberty.poker.userstory;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RemoveUserStory {

    private final UserStoryRepository userStoryRepository;

    public RemoveUserStory(final UserStoryRepository userStoryRepository) {
        this.userStoryRepository = userStoryRepository;
    }

    public void execute(final UUID userStoryId) {
        userStoryRepository.deleteById(userStoryId);
    }
}
