package com.liberty.poker.member;

import com.liberty.poker.AbstractConverter;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.stereotype.Component;

@Component
public class MemberRequestWrapperToMemberConverter extends AbstractConverter<MemberRequest.MemberRequestWrapper, Member> {
    protected MemberRequestWrapperToMemberConverter(ConfigurableConversionService conversionService) {
        super(conversionService);
    }

    @Override
    public Member convert(MemberRequest.MemberRequestWrapper source) {
        return new Member(source.getMemberRequest().getNickName(), source.getPlanningSessionId());
    }
}
