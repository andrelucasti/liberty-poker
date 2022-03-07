package com.liberty.poker.userstory;

import com.liberty.poker.AbstractConverter;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.stereotype.Component;

@Component
public class UserStoryToUserStoryResponseConverter extends AbstractConverter<UserStory, UserStoryResponse> {

    protected UserStoryToUserStoryResponseConverter(ConfigurableConversionService conversionService) {
        super(conversionService);
    }

    @Override
    public UserStoryResponse convert(final UserStory source) {
        return new UserStoryResponse(source.getId(), source.getDescription(), source.getUserStoryStatus(), source.getPlanningSessionId());
    }
}
