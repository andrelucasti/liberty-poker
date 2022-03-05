package com.liberty.poker.planningsession;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PlanningSessionRequest {

    private final String title;
    private final String deckType;

    @JsonCreator
    public PlanningSessionRequest(@JsonProperty("title") final String title,
                                  @JsonProperty("deckType") final String deckType) {
        this.title = title;
        this.deckType = deckType;
    }
}
