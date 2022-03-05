package com.liberty.poker.linksession;

import org.springframework.stereotype.Repository;

@Repository
public class LinkSessionRepositoryImpl implements LinkSessionRepository{

    @Override
    public void save(final LinkSession linkSession) {
        System.out.println("saving link session");
    }
}
