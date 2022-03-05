package com.liberty.poker.linksession;

import org.springframework.stereotype.Service;

@Service
public class GenerateLinkSession {
    private final LinkSessionRepository linkSessionRepository;

    public GenerateLinkSession(final LinkSessionRepository linkSessionRepository) {
        this.linkSessionRepository = linkSessionRepository;
    }

    public void execute(final LinkSession linkSession){
        linkSessionRepository.save(linkSession);
    }
}
