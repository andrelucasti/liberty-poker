package com.liberty.poker.linksession;


import java.util.List;

public interface LinkSessionRepository {
    void save(LinkSession linkSession);
    List<LinkSession> findAll();
}
