package com.liberty.poker.memberuserstory;

import com.liberty.poker.AbstractConverter;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.stereotype.Component;

@Component
public class MemberUserStoryEntityToMemberStoryConverter extends AbstractConverter<MemberUserStoryEntity, MemberUserStory> {

    protected MemberUserStoryEntityToMemberStoryConverter(final ConfigurableConversionService conversionService) {
        super(conversionService);
    }

    @Override
    public MemberUserStory convert(final MemberUserStoryEntity source) {
        return new MemberUserStory(source.getMemberId(), source.getUserStoryId(), source.getPlanningSessionId());
    }
}
