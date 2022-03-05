package com.liberty.poker.linksession;

import org.springframework.stereotype.Component;

@Component
public class LinkSessionEntityToLinkSessionConverter {

    public LinkSession converter(final LinkSessionEntity linkSessionEntity){
        return new LinkSession(linkSessionEntity.getPlanningSessionId());
    }
}
