package com.liberty.poker.planningsession;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Resources;
import com.liberty.poker.AbstractE2ETest;
import com.liberty.poker.linksession.LinkSession;
import com.liberty.poker.member.Member;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.parsing.Parser;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

import static com.liberty.poker.planningsession.PlanningSession.DeckType.FIBONACCI;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

class PlanningSessionControllerE2ETest extends AbstractE2ETest {

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
    void shouldReturnNotContentWhenPlanningSessionNotFound() {
        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .get("/planning-session/")
                .then()
                .status(HttpStatus.NO_CONTENT);
    }

    @Test
    void shouldReturnPlanningSession() {
        final var planningSession = createPlanningSession();
        final var linkSession = createLinkSession(planningSession);

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .get("/planning-session/")
                .then()
                .status(HttpStatus.OK)
                .body("id", Matchers.containsInAnyOrder(
                        Matchers.containsString(planningSession.getId().toString())))
                .body("title", Matchers.containsInAnyOrder(
                        Matchers.containsString(planningSession.getTitle())))
                .body("deckType", Matchers.containsInAnyOrder(
                        Matchers.containsString(planningSession.getDeckType().toString())))
                .body("link", Matchers.containsInAnyOrder(
                        Matchers.containsString(linkSession.getLink())));
    }

    @Test
    void shouldReturn404WhenLinkSessionNotFoundToPlanningSession() {
        createPlanningSession();
        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .get("/planning-session/")
                .then()
                .status(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldReturnHttpCode204WhenPlanningPokerIsDestroyed() {
        final var planningSession = planningSessionRepository
                .save(new PlanningSession("Liberty Planning Poker Session", FIBONACCI));

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .delete("/planning-session/".concat(planningSession.getId().toString()))
                .then()
                .status(HttpStatus.NO_CONTENT);
    }

    @Test
    void shouldReturnHttpCode204WhenPlanningPokerIsDestroyedWithLink() {
        final var planningSession = createPlanningSession();
        createLinkSession(planningSession);

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .delete("/planning-session/".concat(planningSession.getId().toString()))
                .then()
                .status(HttpStatus.NO_CONTENT);
    }

    @Test
    void shouldReturnHttpCode204WhenPlanningPokerIsDestroyedWithAllMembers() {
        final var planningSession = planningSessionRepository
                .save(new PlanningSession("Liberty Planning Poker Session", FIBONACCI));

        memberRepository.save(new Member("Andre Lucas", planningSession.getId()));
        memberRepository.save(new Member("Alexandre Lima", planningSession.getId()));
        memberRepository.save(new Member("Karol Marques", planningSession.getId()));

        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .delete("/planning-session/".concat(planningSession.getId().toString()))
                .then()
                .status(HttpStatus.NO_CONTENT);
    }

    @Test
    void shouldReturn404WhenTryDeleteAndPlanningSessionNotExist() {
        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .delete("/planning-session/".concat(UUID.randomUUID().toString()))
                .then()
                .status(HttpStatus.NOT_FOUND);
    }

    private String createPlanningSessionAsJsonMsg(final String title, final String deckType) throws IOException {
        return Resources.toString(Resources.getResource("planning-session-post.json"),
                        StandardCharsets.UTF_8)
                .replace("{title}", title)
                .replace("{deckType}", deckType);
    }
}