package com.liberty.poker.userstory.converter;

import com.liberty.poker.AbstractConverter;
import com.liberty.poker.userstory.UserStory;
import com.liberty.poker.userstory.UserStoryEntity;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.stereotype.Component;

@Component
public class UserStoryToUserStoryEntityConverter extends AbstractConverter<UserStory, UserStoryEntity> {

    protected UserStoryToUserStoryEntityConverter(final ConfigurableConversionService conversionService) {
        super(conversionService);
    }

    @Override
    public UserStoryEntity convert(final UserStory source) {
        return UserStoryEntity.of(source.getDescription(), source.getUserStoryStatus().toString(), source.getPlanningSessionId());
    }
}
