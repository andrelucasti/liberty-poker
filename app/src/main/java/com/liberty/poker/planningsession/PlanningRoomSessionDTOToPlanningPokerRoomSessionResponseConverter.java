package com.liberty.poker.planningsession;

import com.liberty.poker.AbstractConverter;
import com.liberty.poker.planningroom.PlanningRoomSessionDTO;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.stereotype.Component;

@Component
public class PlanningRoomSessionDTOToPlanningPokerRoomSessionResponseConverter extends AbstractConverter<PlanningRoomSessionDTO, PlanningRoomSessionResponse> {

    protected PlanningRoomSessionDTOToPlanningPokerRoomSessionResponseConverter(final ConfigurableConversionService conversionService) {
        super(conversionService);
    }

    @Override
    public PlanningRoomSessionResponse convert(final PlanningRoomSessionDTO source) {
        return new PlanningRoomSessionResponse(source.getTitle(), source.getMembers(), source.getUserStoryList());
    }
}
