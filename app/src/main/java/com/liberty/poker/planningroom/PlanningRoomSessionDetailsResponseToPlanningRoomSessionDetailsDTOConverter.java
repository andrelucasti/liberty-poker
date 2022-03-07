package com.liberty.poker.planningroom;

import com.liberty.poker.AbstractConverter;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.stereotype.Component;

@Component
public class PlanningRoomSessionDetailsResponseToPlanningRoomSessionDetailsDTOConverter extends AbstractConverter<PlanningRoomSessionDetailsResponse, PlanningRoomSessionDetailsDTO> {

    protected PlanningRoomSessionDetailsResponseToPlanningRoomSessionDetailsDTOConverter(final ConfigurableConversionService conversionService) {
        super(conversionService);
    }

    @Override
    public PlanningRoomSessionDetailsDTO convert(final PlanningRoomSessionDetailsResponse source) {
        return new PlanningRoomSessionDetailsDTO(source.getTitle(), source.getMembers(), source.getUserStories());
    }
}
