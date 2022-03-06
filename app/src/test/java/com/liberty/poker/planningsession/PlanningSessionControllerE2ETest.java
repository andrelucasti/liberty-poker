package com.liberty.poker.planningsession;

import com.google.common.io.Resources;
import com.liberty.poker.AbstractE2ETest;
import com.liberty.poker.linksession.LinkSession;
import com.liberty.poker.member.Member;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.liberty.poker.planningsession.PlanningSession.DeckType.FIBONACCI;
import static org.hamcrest.Matchers.any;
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
    void shouldReturnError400WhenDeckTypeNotExists() {

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
        final var planningSession = planningSessionRepository
                .save(new PlanningSession("Liberty Planning Poker Session", FIBONACCI));

        linkSessionRepository.save(new LinkSession(planningSession.getId()));

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

    private String createPlanningSessionAsJsonMsg(final String title, final String deckType) throws IOException {
        return Resources.toString(Resources.getResource("planning-session-post.json"),
                        StandardCharsets.UTF_8)
                .replace("{title}", title)
                .replace("{deckType}", deckType);
    }
}