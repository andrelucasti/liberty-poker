package com.liberty.poker.linksession;

import org.springframework.stereotype.Service;

@Service
public class GenerateLinkSession {
    private final LinkSessionRepository linkSessionRepository;

    public GenerateLinkSession(final LinkSessionRepository linkSessionRepository) {
        this.linkSessionRepository = linkSessionRepository;
    }

    public LinkSession execute(final LinkSession linkSession){
        return linkSessionRepository.save(linkSession);
    }
}
