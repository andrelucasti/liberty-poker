package com.liberty.poker.userstory;

import com.liberty.poker.AbstractConverter;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.stereotype.Component;

@Component
public class UserStoryToUserStoryResponse extends AbstractConverter<UserStory, UserStoryResponse> {

    protected UserStoryToUserStoryResponse(ConfigurableConversionService conversionService) {
        super(conversionService);
    }

    @Override
    public UserStoryResponse convert(final UserStory source) {
        return new UserStoryResponse(source.getDescription(), source.getPlanningSessionId());
    }
}
