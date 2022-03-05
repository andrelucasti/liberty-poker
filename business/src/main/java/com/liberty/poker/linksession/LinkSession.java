package com.liberty.poker.linksession;

import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter
@ToString
public class LinkSession {
    private static final String ROOM_URI = "/room/";

    private final String link;
    private final UUID planningSessionId;

    public LinkSession(final UUID planningSessionId) {
        this.planningSessionId = planningSessionId;
        this.link = this.generateLink();
    }

    private String generateLink() {
        return ROOM_URI.concat(planningSessionId.toString());
    }
}
