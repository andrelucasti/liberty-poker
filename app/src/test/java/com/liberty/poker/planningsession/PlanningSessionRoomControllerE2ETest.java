package com.liberty.poker.planningsession;

import com.liberty.poker.AbstractE2ETest;
import com.liberty.poker.member.Member;
import com.liberty.poker.userstory.UserStory;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;

import static com.liberty.poker.planningsession.PlanningSession.DeckType.FIBONACCI;


class PlanningSessionRoomControllerE2ETest extends AbstractE2ETest {

    @Test
    void shouldJoinInPlanningSessionWhenLinkSessionWasGeneratedAndExists() throws IOException {
        final var planningSession = createPlanningSession();
        final var memberPostMsg = createMemberAsJsonMsg("Andre Lucas");

        RestAssuredMockMvc.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(memberPostMsg)
                .post("/room/".concat(planningSession.getId().toString()))
                .then()
                .status(HttpStatus.CREATED);
    }

    @Test
    void shouldReturnMembersWhenJoinInPlanningSession() throws IOException {
        final var planningSession = createPlanningSession();
        final var memberPostMsg = createMemberAsJsonMsg("Andre Lucas");

        RestAssuredMockMvc.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(memberPostMsg)
                .post("/room/".concat(planningSession.getId().toString()))
                .then()
                .status(HttpStatus.CREATED);
    }

    @Test
    void shouldReturnUserStoriesWhenJoinInPlanningSession() throws IOException {
        final var planningSession = createPlanningSession();
        final var memberPostMsg = createMemberAsJsonMsg("Andre Lucas");

        RestAssuredMockMvc.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(memberPostMsg)
                .post("/room/".concat(planningSession.getId().toString()))
                .then()
                .status(HttpStatus.CREATED);
    }

    @Test
    void shouldReturnMembersInBodyWhenGetPlanningRoomSession() {
        final var planningSession = createPlanningSession();
        memberRepository.save(new Member("Andre Lucas", planningSession.getId()));
        memberRepository.save(new Member("Vjay", planningSession.getId()));

        RestAssuredMockMvc.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .get("/room/".concat(planningSession.getId().toString()))
                .then()
                .status(HttpStatus.OK)
                .body("members.nickName", Matchers.containsInAnyOrder("Andre Lucas", "Vjay"));

    }

    @Test
    void shouldReturnUserStoriesInBodyWhenGetPlanningRoomSession() {
        final var planningSession = createPlanningSession();
        memberRepository.save(new Member("Andre Lucas", planningSession.getId()));
        memberRepository.save(new Member("Vjay", planningSession.getId()));

        userStoryRepository.save(new UserStory("story1", planningSession.getId()));
        userStoryRepository.save(new UserStory("story2", planningSession.getId()));


        RestAssuredMockMvc.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .get("/room/".concat(planningSession.getId().toString()))
                .then()
                .status(HttpStatus.OK)
                .body("stories.description", Matchers.containsInAnyOrder("story1", "story2"));

    }

    private PlanningSession createPlanningSession() {
        return planningSessionRepository
                .save(new PlanningSession("Liberty Planning Poker Session", FIBONACCI));
    }
}