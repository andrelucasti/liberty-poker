package com.liberty.poker.planningsession;

import com.liberty.poker.AbstractConverter;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.stereotype.Component;

@Component
public class PlanningPokerRoomSessionDTOToPlanningPokerRoomSessionResponseConverter extends AbstractConverter<PlanningPokerRoomSessionDTO, PlanningPokerRoomSessionResponse> {

    protected PlanningPokerRoomSessionDTOToPlanningPokerRoomSessionResponseConverter(ConfigurableConversionService conversionService) {
        super(conversionService);
    }

    @Override
    public PlanningPokerRoomSessionResponse convert(PlanningPokerRoomSessionDTO source) {
        return new PlanningPokerRoomSessionResponse(source.getTitle(), source.getMembers());
    }
}
