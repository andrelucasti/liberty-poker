package com.liberty.poker.planningroom.converter;

import com.liberty.poker.AbstractConverter;
import com.liberty.poker.planningroom.PlanningRoomSessionDTO;
import com.liberty.poker.planningroom.PlanningRoomSessionResponse;
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
