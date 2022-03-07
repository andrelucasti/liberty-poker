package com.liberty.poker.planningroom;

import com.liberty.poker.AbstractConverter;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.stereotype.Component;

@Component
public class PlanningRoomSessionDTOToPlanningRoomSessionResponseConverter extends AbstractConverter<PlanningRoomSessionDTO, PlanningRoomSessionResponse> {

    protected PlanningRoomSessionDTOToPlanningRoomSessionResponseConverter(final ConfigurableConversionService conversionService) {
        super(conversionService);
    }

    @Override
    public PlanningRoomSessionResponse convert(final PlanningRoomSessionDTO source) {
        return new PlanningRoomSessionResponse(source.getTitle(), source.getMembers(), source.getUserStoryList());
    }
}
