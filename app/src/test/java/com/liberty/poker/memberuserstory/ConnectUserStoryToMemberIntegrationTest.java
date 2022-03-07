package com.liberty.poker.memberuserstory;

import com.liberty.poker.AbstractIntegrationTests;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

class ConnectUserStoryToMemberIntegrationTest extends AbstractIntegrationTests {

    @Autowired
    private ConnectUserStoryToMember subject;

    @Autowired
    private MemberUserStoryRepository memberUserStoryRepository;

    @Test
    void shouldCreateUserStoriesToMemberInTheSamePlanningRoomSession() {
        final var planningSession = createPlanningSession();
        final var userStory1 = createUserStory(planningSession);
        final var userStory2 = createUserStory(planningSession);
        final var member = createMember(planningSession);

        subject.execute(member.getId(), List.of(userStory1, userStory2), planningSession.getId());

        final var memberUserStoryList = memberUserStoryRepository.findAll();

        Assertions.assertThat(memberUserStoryList).isNotEmpty();
        Assertions.assertThat(memberUserStoryList).hasSize(2);

        Assertions.assertThat(memberUserStoryList).contains(
                new MemberUserStory(member.getId(), userStory1.getId(), planningSession.getId()),
                new MemberUserStory(member.getId(), userStory2.getId(), planningSession.getId()));
    }
}