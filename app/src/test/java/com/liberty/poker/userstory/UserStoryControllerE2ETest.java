package com.liberty.poker.userstory;

import com.google.common.io.Resources;
import com.liberty.poker.AbstractE2ETest;
import com.liberty.poker.planningsession.PlanningSession;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

class UserStoryControllerE2ETest extends AbstractE2ETest {

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

    private String createUserStoryAsJsonMsg(final String description, final String planningSessionId) throws IOException {
        return Resources.toString(Resources.getResource("user-story-post.json"),
                        StandardCharsets.UTF_8)
                .replace("{description}", description)
                .replace("{planningSessionId}", planningSessionId);
    }
}