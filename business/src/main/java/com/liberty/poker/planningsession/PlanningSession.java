package com.liberty.poker.planningsession;

import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter
@ToString
public class PlanningSession {
    private UUID id;
    private final String title;
    private final DeckType deckType;

    public PlanningSession(final String title,
                           final DeckType deckType) {
        this.title = title;
        this.deckType = deckType;
    }

    public PlanningSession(final UUID id,
                           final String title,
                           final DeckType deckType) {
        this.id = id;
        this.title = title;
        this.deckType = deckType;
    }


    public enum DeckType{
        FIBONACCI
    }
}

