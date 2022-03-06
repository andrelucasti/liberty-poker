package com.liberty.poker.member;

import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class MemberRepositoryImpl implements MemberRepository {

    private final MemberRepositoryEntity memberRepositoryEntity;
    private final ConversionService converter;

    public MemberRepositoryImpl(final MemberRepositoryEntity memberRepositoryEntity,
                                final ConversionService converter) {
        this.memberRepositoryEntity = memberRepositoryEntity;
        this.converter = converter;
    }

    @Override
    public Member save(final Member member) {
        final var memberEntity = memberRepositoryEntity
                .save(Objects.requireNonNull(converter.convert(member, MemberEntity.class)));
        return converter.convert(memberEntity, Member.class);
    }

    @Override
    public List<Member> findAll() {
        return memberRepositoryEntity.findAll()
                        .stream()
                        .map(memberEntity -> converter.convert(memberEntity, Member.class))
                        .collect(Collectors.toList());
    }

    @Override
    public List<Member> findMembersBy(final UUID planningSessionId) {
        return memberRepositoryEntity.findMembersByPlanningSessionId(planningSessionId);
    }

    @Override
    @Transactional
    public void deleteMemberBy(final UUID planningSessionId) {
        memberRepositoryEntity.deleteByPlanningSessionId(planningSessionId);
    }
}
