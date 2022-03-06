package com.liberty.poker.userstory;

import org.springframework.stereotype.Service;

@Service
public class AddUserStory {
    private final UserStoryRepository userStoryRepository;

    public AddUserStory(final UserStoryRepository userStoryRepository) {
        this.userStoryRepository = userStoryRepository;
    }

    public UserStory execute(final UserStory userStory) {
        return userStoryRepository.save(userStory);
    }
}
