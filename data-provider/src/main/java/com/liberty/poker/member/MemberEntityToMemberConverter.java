package com.liberty.poker.member;

import com.liberty.poker.AbstractConverter;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.stereotype.Component;

@Component
public class MemberEntityToMemberConverter extends AbstractConverter<MemberEntity, Member> {

    protected MemberEntityToMemberConverter(final ConfigurableConversionService conversionService) {
        super(conversionService);
    }

    @Override
    public Member convert(final MemberEntity source) {
        return new Member(source.getNickName(), source.getPlanningSessionId());
    }
}
