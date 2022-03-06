package com.liberty.poker;

import com.google.common.io.Resources;
import com.liberty.poker.linksession.LinkSessionRepository;
import com.liberty.poker.member.MemberRepository;
import com.liberty.poker.planningsession.PlanningSessionRepository;
import com.liberty.poker.userstory.UserStoryRepository;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractE2ETest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
    }


    @Autowired
    protected PlanningSessionRepository planningSessionRepository;

    @Autowired
    protected LinkSessionRepository linkSessionRepository;

    @Autowired
    protected MemberRepository memberRepository;

    @Autowired
    protected UserStoryRepository userStoryRepository;

    protected String createMemberAsJsonMsg(final String nickName) throws IOException {
        return Resources.toString(Resources.getResource("member-join-post.json"),
                        StandardCharsets.UTF_8)
                .replace("{nickName}", nickName);
    }
}
