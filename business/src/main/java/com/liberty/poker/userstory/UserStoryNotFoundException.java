package com.liberty.poker.userstory;

public class UserStoryNotFoundException extends Exception {
    public UserStoryNotFoundException(String message) {
        super(message);
    }

    public UserStoryNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
