package com.liberty.poker.planningsession.converter;

import com.liberty.poker.AbstractConverter;
import com.liberty.poker.planningsession.PlanningSessionDTO;
import com.liberty.poker.planningsession.PlanningSessionResponse;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.stereotype.Component;

@Component
public class PlanningSessionDTOToPlanningSessionResponseConverter extends AbstractConverter<PlanningSessionDTO, PlanningSessionResponse> {

    protected PlanningSessionDTOToPlanningSessionResponseConverter(ConfigurableConversionService conversionService) {
        super(conversionService);
    }

    @Deprecated
    public PlanningSessionResponse converter(final PlanningSessionDTO planningSessionDTO){
        final var newPlanningSession = planningSessionDTO.getNewPlanningSession();
        final var newLinkSession = planningSessionDTO.getNewLinkSession();

        return new PlanningSessionResponse(newPlanningSession.getId(),
                newPlanningSession.getTitle(),
                newPlanningSession.getDeckType().toString(),
                newLinkSession.getLink());
    }

    @Override
    public PlanningSessionResponse convert(final PlanningSessionDTO source) {
        final var newPlanningSession = source.getNewPlanningSession();
        final var newLinkSession = source.getNewLinkSession();

        return new PlanningSessionResponse(newPlanningSession.getId(),
                newPlanningSession.getTitle(),
                newPlanningSession.getDeckType().toString(),
                newLinkSession.getLink());
    }
}
