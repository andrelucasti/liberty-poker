package com.liberty.poker.member;

import com.liberty.poker.AbstractConverter;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.stereotype.Component;

@Component
public class MemberToMemberEntityConverter extends AbstractConverter<Member, MemberEntity> {

    protected MemberToMemberEntityConverter(final ConfigurableConversionService conversionService) {
        super(conversionService);
    }

    @Override
    public MemberEntity convert(Member source) {
        return MemberEntity.of(source.getNickName(), source.getPlanningSessionId());
    }
}
