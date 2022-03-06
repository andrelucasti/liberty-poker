package com.liberty.poker.linksession;


import java.util.List;

public interface LinkSessionRepository {
    LinkSession save(LinkSession linkSession);
    List<LinkSession> findAll();
}
