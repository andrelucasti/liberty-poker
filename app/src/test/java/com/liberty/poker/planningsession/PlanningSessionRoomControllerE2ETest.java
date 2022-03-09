package com.liberty.poker.planningsession;

import com.google.common.io.Resources;
import com.liberty.poker.AbstractE2ETest;
import com.liberty.poker.member.Member;
import com.liberty.poker.memberuserstory.MemberUserStory;
import com.liberty.poker.userstory.UserStory;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.stream.Collectors;


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
    void shouldReturn404WhenPlanningSessionNotFound() throws IOException {
        final var memberPostMsg = createMemberAsJsonMsg("Andre Lucas");
        RestAssuredMockMvc.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(memberPostMsg)
                .post("/room/".concat(UUID.randomUUID().toString()))
                .then()
                .status(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldReturn404WhenTryGetDetailsButPlanningSessionNotFound() {
        RestAssuredMockMvc.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .get("/room/".concat(UUID.randomUUID().toString()))
                .then()
                .status(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldReturnOkWhenUserStoryWhenVoteIsEnabled() {
        final var planningSession = createPlanningSession();

        final var userStory1 = createPendingUserStoryForVote(planningSession);
        final var userStory2 = createPendingUserStoryForVote(planningSession);

        RestAssuredMockMvc.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .put("/room/".concat(planningSession.getId().toString()).concat("/start"))
                .then()
                .status(HttpStatus.OK);

        final var userStoryList = userStoryRepository.findAll();
        final var userStoryStatuses = userStoryList.stream().map(UserStory::getUserStoryStatus).collect(Collectors.toList());
        final var userStoryIds = userStoryList.stream().map(UserStory::getId).collect(Collectors.toList());

        Assertions.assertThat(userStoryList).hasSize(2);
        Assertions.assertThat(userStoryStatuses).containsOnly(UserStory.UserStoryStatus.VOTING);
        Assertions.assertThat(userStoryIds).contains(userStory1.getId(), userStory2.getId());
    }

    @Test
    void shouldReturnOkWhenUserStoryIsDisableToVotes() throws IOException {
        final var planningSession = createPlanningSession();
        final var story = userStoryRepository.save(new UserStory(UUID.randomUUID(), "story1", UserStory.UserStoryStatus.VOTING, planningSession.getId()));

        RestAssuredMockMvc.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .put("/room/".concat(planningSession.getId().toString()).concat("/stop"))
                .then()
                .status(HttpStatus.OK);

        final var userStoryStatus = userStoryRepository.findById(story.getId()).get().getUserStoryStatus();

        Assertions.assertThat(userStoryStatus).isEqualTo(UserStory.UserStoryStatus.VOTED);
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
                .put("/room/".concat(planningSession.getId().toString()).concat("/vote"))
                .then()
                .status(HttpStatus.OK);
    }

    @Test
    void shouldReturnBadRequestWhenUserStoryIsVotedStatus() throws IOException {
        final var planningSession = createPlanningSession();
        final var story = userStoryRepository.save(new UserStory(UUID.randomUUID(), "story1", UserStory.UserStoryStatus.VOTED, planningSession.getId()));
        final var member = memberRepository.save(new Member("Andre Lucas", planningSession.getId()));
        final var memberUserStory = memberUserStoryRepository.save(new MemberUserStory(member.getId(), story.getId(), planningSession.getId()));

        RestAssuredMockMvc.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(createMemberUserStoryAsJsonMsg(memberUserStory.getMemberId(), memberUserStory.getUserStoryId(), "10"))
                .put("/room/".concat(planningSession.getId().toString()).concat("/vote"))
                .then()
                .status(HttpStatus.BAD_REQUEST);
    }

    @Test
    void shouldReturnBadRequestWhenUserStoryIsPendingStatus() throws IOException {
        final var planningSession = createPlanningSession();
        final var story = userStoryRepository.save(new UserStory(UUID.randomUUID(), "story1", UserStory.UserStoryStatus.PENDING, planningSession.getId()));
        final var member = memberRepository.save(new Member("Andre Lucas", planningSession.getId()));
        final var memberUserStory = memberUserStoryRepository.save(new MemberUserStory(member.getId(), story.getId(), planningSession.getId()));

        RestAssuredMockMvc.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(createMemberUserStoryAsJsonMsg(memberUserStory.getMemberId(), memberUserStory.getUserStoryId(), "10"))
                .put("/room/".concat(planningSession.getId().toString()).concat("/vote"))
                .then()
                .status(HttpStatus.BAD_REQUEST);
    }

    private String createMemberUserStoryAsJsonMsg(final UUID memberId, UUID userStoryId, final String voteValue) throws IOException {
        return Resources.toString(Resources.getResource("member-user-story-post.json"),
                        StandardCharsets.UTF_8)
                .replace("{memberId}", memberId.toString())
                .replace("{userStoryId}", userStoryId.toString())
                .replace("{value}", voteValue);
    }
    private UserStory createPendingUserStoryForVote(final PlanningSession planningSession) {
       return  userStoryRepository.save(new UserStory("anyDesc", planningSession.getId()));
    }

}