package com.liberty.poker.planningsession;

public class PlanningSessionNotFoundException extends Exception {

    public PlanningSessionNotFoundException(String message) {
        super(message);
    }

    public PlanningSessionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
