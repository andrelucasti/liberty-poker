package com.liberty.poker.userstory;

import com.liberty.poker.AbstractConverter;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.stereotype.Component;

@Component
public class UserStoryRequestToUserStoryConverter extends AbstractConverter<UserStoryRequest, UserStory> {

    protected UserStoryRequestToUserStoryConverter(ConfigurableConversionService conversionService) {
        super(conversionService);
    }

    @Override
    public UserStory convert(final UserStoryRequest source) {
        return new UserStory(source.getDescription(), source.getPlanningSessionId());
    }
}
