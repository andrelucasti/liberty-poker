package com.liberty.poker;

import com.liberty.poker.linksession.GenerateLinkSession;
import com.liberty.poker.linksession.LinkSession;
import com.liberty.poker.linksession.LinkSessionRepository;
import com.liberty.poker.member.MemberRepository;
import com.liberty.poker.planningsession.PlanningSession;
import com.liberty.poker.planningsession.PlanningSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public abstract class AbstractIntegrationTests {

    @Autowired
    protected PlanningSessionRepository planningSessionRepository;

    @Autowired
    protected LinkSessionRepository linkSessionRepository;

    @Autowired
    protected MemberRepository memberRepository;

    protected PlanningSession createPlanningPokerSession() {
        return planningSessionRepository.save(
                new PlanningSession("LibertyPlanningPokerSession", PlanningSession.DeckType.FIBONACCI));
    }

    protected void createLinkSession(PlanningSession planningPokerSession) {
        linkSessionRepository.save(new LinkSession(planningPokerSession.getId()));
    }
}
