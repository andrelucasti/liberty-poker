package com.liberty.poker.linksession;


import java.util.List;
import java.util.UUID;

public interface LinkSessionRepository {
    LinkSession save(LinkSession linkSession);
    List<LinkSession> findAll();
    void deleteBy(UUID planningSessionId);
}
