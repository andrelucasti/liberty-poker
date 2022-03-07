package com.liberty.poker.userstory;

import com.google.common.io.Resources;
import com.liberty.poker.planningsession.PlanningSession;
import com.liberty.poker.planningsession.PlanningSessionRepository;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.internal.matchers.Matches;
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
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserStoryControllerE2ETest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private PlanningSessionRepository planningSessionRepository;

    @Autowired
    private UserStoryRepository userStoryRepository;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
    }

    @Test
    void shouldReturnHttpCode201WhenUserStoryIsCreated() throws IOException {
        final var planningSession = planningSessionRepository
                .save(new PlanningSession("Liberty Planning Poker Session", FIBONACCI));

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
        final var planningSession = planningSessionRepository
                .save(new PlanningSession("Liberty Planning Poker Session", FIBONACCI));

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
        final var planningSession = planningSessionRepository
                .save(new PlanningSession("Liberty Planning Poker Session", FIBONACCI));

        final var userStory = userStoryRepository.save(new UserStory("anyDesc", planningSession.getId()));

        RestAssuredMockMvc.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .delete("/user-story/".concat(userStory.getId().toString()))
                .then()
                .status(HttpStatus.NO_CONTENT);
    }

    @Test
    void shouldReturnOkWhenUserStoryWhenVoteIsStarted() throws IOException {
        final var planningSession = planningSessionRepository
                .save(new PlanningSession("Liberty Planning Poker Session", FIBONACCI));

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


    private String createUserStoryAsJsonMsg(final String description, final String planningSessionId) throws IOException {
        return Resources.toString(Resources.getResource("user-story-post.json"),
                        StandardCharsets.UTF_8)
                .replace("{description}", description)
                .replace("{planningSessionId}", planningSessionId);
    }
}