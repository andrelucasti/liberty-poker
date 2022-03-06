package com.liberty.poker.planningsession;

import com.google.common.io.Resources;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
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

import static org.hamcrest.Matchers.*;

//TODO we can put this configuration in a abstract E2E class - AbstractE2ETest
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PlanningSessionControllerE2ETest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
    }

    @Test
    void shouldReturnCode201WhenPlanningSessionIsCreated() throws IOException {
        final var planningPokerPostMsg
                = createPlanningSessionAsJsonMsg("Liberty Planning Session", "FIBONACCI");

        RestAssuredMockMvc.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(planningPokerPostMsg)
                .post("/planning-session")
                .then()
                .status(HttpStatus.CREATED);
    }

    @Test
    void shouldReturnPlanningSessionResponseWhenPlanningSessionIsCreated() throws IOException {
        final var planningSessionTitle = "Liberty Planning Session";
        final var deckType = "FIBONACCI";
        final var planningPokerPostMsg
                = createPlanningSessionAsJsonMsg(planningSessionTitle, deckType);

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(planningPokerPostMsg)
                .post("/planning-session")
                .then()
                .status(HttpStatus.CREATED)
                .body("id", any(String.class))
                .body("title", equalTo(planningSessionTitle))
                .body("deckType", equalTo(deckType))
                .body("link", containsString("room/"));

    }

    @Test
    void shouldReturnError400WhenDeckTypeNotExists() {

    }

    private String createPlanningSessionAsJsonMsg(final String title, final String deckType) throws IOException {
        return Resources.toString(Resources.getResource("planning-session-post.json"),
                        StandardCharsets.UTF_8)
                .replace("{title}", title)
                .replace("{deckType}", deckType);
    }
}