package com.liberty.poker.linksession;


import com.google.common.annotations.VisibleForTesting;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LinkSessionRepository {
    LinkSession save(LinkSession linkSession);
    List<LinkSession> findAll();
    void deleteBy(UUID planningSessionId);

    @VisibleForTesting
    void deleteAll();
    Optional<LinkSession> findByPlanningSession(UUID id);
}
