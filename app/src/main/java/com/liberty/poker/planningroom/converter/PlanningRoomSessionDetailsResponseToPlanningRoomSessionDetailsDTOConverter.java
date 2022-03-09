package com.liberty.poker.planningroom.converter;

import com.liberty.poker.AbstractConverter;
import com.liberty.poker.planningroom.PlanningRoomSessionDetailsDTO;
import com.liberty.poker.planningroom.PlanningRoomSessionDetailsResponse;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.stereotype.Component;

@Component
public class PlanningRoomSessionDetailsResponseToPlanningRoomSessionDetailsDTOConverter extends AbstractConverter<PlanningRoomSessionDetailsResponse, PlanningRoomSessionDetailsDTO> {

    protected PlanningRoomSessionDetailsResponseToPlanningRoomSessionDetailsDTOConverter(final ConfigurableConversionService conversionService) {
        super(conversionService);
    }

    @Override
    public PlanningRoomSessionDetailsDTO convert(final PlanningRoomSessionDetailsResponse source) {
        return null;
    }
}
