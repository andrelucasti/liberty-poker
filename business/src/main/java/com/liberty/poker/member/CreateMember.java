package com.liberty.poker.member;

import org.springframework.stereotype.Service;

@Service
public class CreateMember {

    private final MemberRepository memberRepository;

    public CreateMember(final MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void execute(final Member member) {
        memberRepository.save(member);
    }
}
