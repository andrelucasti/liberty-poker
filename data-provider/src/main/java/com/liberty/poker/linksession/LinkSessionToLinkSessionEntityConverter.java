package com.liberty.poker.linksession;

import org.springframework.stereotype.Component;

@Component
public class LinkSessionToLinkSessionEntityConverter {

    public LinkSessionEntity converter(final LinkSession linkSession){
        return LinkSessionEntity.of(linkSession.getLink(), linkSession.getPlanningSessionId());
    }
}
