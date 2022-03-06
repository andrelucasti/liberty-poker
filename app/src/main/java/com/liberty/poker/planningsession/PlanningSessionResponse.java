package com.liberty.poker.planningsession;

import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter
@ToString
public class PlanningSessionResponse {
    private final UUID id;
    private final String title;
    private final String deckType;
    private final String link;

    public PlanningSessionResponse(final UUID id,
                                   final String title,
                                   final String deckType,
                                   final String link) {
        this.id = id;
        this.title = title;
        this.deckType = deckType;
        this.link = link;
    }
}
