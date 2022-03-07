package com.liberty.poker.userstory;

import com.liberty.poker.AbstractConverter;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.stereotype.Component;

@Component
public class UserStoryEntityToUserStoryConverter extends AbstractConverter<UserStoryEntity, UserStory> {

    protected UserStoryEntityToUserStoryConverter(final ConfigurableConversionService conversionService) {
        super(conversionService);
    }

    @Override
    public UserStory convert(final UserStoryEntity source) {
        return new UserStory(source.getUuid(), source.getDescription(), UserStory.UserStoryStatus.valueOf(source.getStatus()), source.getPlanningSessionId());
    }
}
