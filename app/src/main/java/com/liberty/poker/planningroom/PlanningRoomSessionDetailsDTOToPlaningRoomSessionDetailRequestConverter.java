package com.liberty.poker.planningroom;

import com.liberty.poker.AbstractConverter;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.stereotype.Component;

@Component
public class PlanningRoomSessionDetailsDTOToPlaningRoomSessionDetailRequestConverter extends AbstractConverter<PlanningRoomSessionDetailsDTO, PlanningRoomSessionDetailsResponse> {
    protected PlanningRoomSessionDetailsDTOToPlaningRoomSessionDetailRequestConverter(final ConfigurableConversionService conversionService) {
        super(conversionService);
    }

    @Override
    public PlanningRoomSessionDetailsResponse convert(final PlanningRoomSessionDetailsDTO source) {
        return new PlanningRoomSessionDetailsResponse(source.getTitle(), source.getMemberRoomDTOS(), source.getUserStoriesDTOS());
    }
}
