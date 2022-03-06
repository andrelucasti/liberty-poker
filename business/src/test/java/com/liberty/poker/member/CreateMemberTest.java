package com.liberty.poker.member;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class CreateMemberTest {

    private CreateMember subject;

    @Mock
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        subject = new CreateMember(memberRepository);
    }

    @Test
    void shouldCreateMember() {
        final var member = new Member("Andre Lucas", UUID.randomUUID());

        subject.execute(member);

        Mockito.verify(memberRepository).save(Mockito.eq(member));
    }
}