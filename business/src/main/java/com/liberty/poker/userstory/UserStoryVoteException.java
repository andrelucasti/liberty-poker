package com.liberty.poker.userstory;

public class UserStoryVoteException extends Exception{
    public UserStoryVoteException(String message) {
        super(message);
    }

    public UserStoryVoteException(String message, Throwable cause) {
        super(message, cause);
    }
}
