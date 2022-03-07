package com.liberty.poker;

import com.liberty.poker.linksession.LinkSession;
import com.liberty.poker.linksession.LinkSessionRepository;
import com.liberty.poker.member.Member;
import com.liberty.poker.member.MemberRepository;
import com.liberty.poker.planningsession.PlanningSession;
import com.liberty.poker.planningsession.PlanningSessionRepository;
import com.liberty.poker.userstory.UserStory;
import com.liberty.poker.userstory.UserStoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public abstract class AbstractIntegrationTests {


    @BeforeEach
    void setUp() {
        userStoryRepository.deleteAll();
        memberRepository.deleteAll();
        linkSessionRepository.deleteAll();
        planningSessionRepository.deleteAll();
    }

    @Autowired
    protected PlanningSessionRepository planningSessionRepository;

    @Autowired
    protected LinkSessionRepository linkSessionRepository;

    @Autowired
    protected MemberRepository memberRepository;

    @Autowired
    protected UserStoryRepository userStoryRepository;

    protected PlanningSession createPlanningSession() {
        return planningSessionRepository.save(
                new PlanningSession("LibertyPlanningPokerSession", PlanningSession.DeckType.FIBONACCI));
    }

    protected void createLinkSession(final PlanningSession planningPokerSession) {
        linkSessionRepository.save(new LinkSession(planningPokerSession.getId()));
    }

    protected UserStory createUserStory(final PlanningSession planningSession) {
        return userStoryRepository.save(new UserStory("anyDesc", planningSession.getId()));
    }

    protected Member createMember(final PlanningSession planningSession) {
        return memberRepository.save(new Member("anyMember", planningSession.getId()));
    }
}
