package com.liberty.poker.userstory;

import com.google.common.io.Resources;
import com.liberty.poker.member.Member;
import com.liberty.poker.member.MemberRepository;
import com.liberty.poker.memberuserstory.MemberUserStory;
import com.liberty.poker.memberuserstory.MemberUserStoryRepository;
import com.liberty.poker.planningsession.PlanningSession;
import com.liberty.poker.planningsession.PlanningSessionRepository;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static com.liberty.poker.planningsession.PlanningSession.DeckType.FIBONACCI;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserStoryControllerE2ETest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private PlanningSessionRepository planningSessionRepository;

    @Autowired
    private UserStoryRepository userStoryRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberUserStoryRepository memberUserStoryRepository;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
    }

    @Test
    void shouldReturnHttpCode201WhenUserStoryIsCreated() throws IOException {
        final PlanningSession planningSession = createPlanningSession();

        final var userStoryPostMsg = createUserStoryAsJsonMsg("Any Description",
                planningSession.getId().toString());

        RestAssuredMockMvc.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(userStoryPostMsg)
                .post("/user-story")
                .then()
                .status(HttpStatus.CREATED);

    }

    @Test
    void shouldReturnUserStoryResponseWhenUserStoryIsCreated() throws IOException {
        final PlanningSession planningSession = createPlanningSession();

        final var userStoryDescription = "Any Description";
        final var userStoryPostMsg = createUserStoryAsJsonMsg(userStoryDescription,
                planningSession.getId().toString());

        RestAssuredMockMvc.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(userStoryPostMsg)
                .post("/user-story")
                .then()
                .status(HttpStatus.CREATED)
                .body("id", Matchers.any(String.class))
                .body("description", Matchers.equalTo(userStoryDescription))
                .body("planningSessionId", Matchers.equalTo(planningSession.getId().toString()))
                .body("status", Matchers.equalTo(UserStory.UserStoryStatus.PENDING.toString()));
    }

    @Test
    void shouldReturnNoContentWhenUserStoryIsRemoved() {
        final PlanningSession planningSession = createPlanningSession();

        final var userStory = userStoryRepository.save(new UserStory("anyDesc", planningSession.getId()));

        RestAssuredMockMvc.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .delete("/user-story/".concat(userStory.getId().toString()))
                .then()
                .status(HttpStatus.NO_CONTENT);
    }

    @Test
    void shouldReturnOkWhenUserStoryWhenVoteIsEnabled() throws IOException {
        final PlanningSession planningSession = createPlanningSession();

        final var userStoryDescription = "Any Description";
        final var userStoryPostMsg = createUserStoryAsJsonMsg(userStoryDescription,
                planningSession.getId().toString());

        RestAssuredMockMvc.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(userStoryPostMsg)
                .put("/user-story/start/".concat(planningSession.getId().toString()))
                .then()
                .status(HttpStatus.OK);

    }

    @Test
    void shouldReturnOkWhenUserStoryIsVoted() throws IOException {
        final var planningSession = createPlanningSession();
        final var story = userStoryRepository.save(new UserStory(UUID.randomUUID(), "story1", UserStory.UserStoryStatus.VOTING, planningSession.getId()));
        final var member = memberRepository.save(new Member("Andre Lucas", planningSession.getId()));
        final var memberUserStory = memberUserStoryRepository.save(new MemberUserStory(member.getId(), story.getId(), planningSession.getId()));

        RestAssuredMockMvc.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(createMemberUserStoryAsJsonMsg(memberUserStory.getMemberId(), memberUserStory.getUserStoryId(), "10"))
                .put("/user-story/vote/".concat(planningSession.getId().toString()))
                .then()
                .status(HttpStatus.OK);
    }

    @Test
    void shouldReturnOkWhenUserStoryIsDisableToVotes() throws IOException {
        final var planningSession = createPlanningSession();
        final var story = userStoryRepository.save(new UserStory(UUID.randomUUID(), "story1", UserStory.UserStoryStatus.VOTING, planningSession.getId()));

        RestAssuredMockMvc.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .put("/user-story/stop/".concat(planningSession.getId().toString()))
                .then()
                .status(HttpStatus.OK);

        final var userStoryStatus = userStoryRepository.findById(story.getId()).get().getUserStoryStatus();

        Assertions.assertThat(userStoryStatus).isEqualTo(UserStory.UserStoryStatus.VOTED);
    }

    private String createUserStoryAsJsonMsg(final String description, final String planningSessionId) throws IOException {
        return Resources.toString(Resources.getResource("user-story-post.json"),
                        StandardCharsets.UTF_8)
                .replace("{description}", description)
                .replace("{planningSessionId}", planningSessionId);
    }

    private String createMemberUserStoryAsJsonMsg(final UUID memberId, UUID userStoryId, final String value) throws IOException {
        return Resources.toString(Resources.getResource("member-user-story-post.json"),
                        StandardCharsets.UTF_8)
                .replace("{memberId}", memberId.toString())
                .replace("{userStoryId}", userStoryId.toString())
                .replace("{value}", value);
    }


    private PlanningSession createPlanningSession() {
        return planningSessionRepository
                .save(new PlanningSession("Liberty Planning Poker Session", FIBONACCI));
    }
}