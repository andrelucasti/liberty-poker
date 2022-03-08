package com.liberty.poker.planningroom;

import com.liberty.poker.AbstractConverter;
import com.liberty.poker.planningsession.PlanningPokerSessionDTO;
import com.liberty.poker.planningsession.PlanningSessionResponse;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.stereotype.Component;

@Component
public class PlanningSessionDTOToPlanningSessionResponseConverter extends AbstractConverter<PlanningPokerSessionDTO, PlanningSessionResponse> {

    protected PlanningSessionDTOToPlanningSessionResponseConverter(ConfigurableConversionService conversionService) {
        super(conversionService);
    }

    @Deprecated
    public PlanningSessionResponse converter(final PlanningPokerSessionDTO planningPokerSessionDTO){
        final var newPlanningSession = planningPokerSessionDTO.getNewPlanningSession();
        final var newLinkSession = planningPokerSessionDTO.getNewLinkSession();

        return new PlanningSessionResponse(newPlanningSession.getId(),
                newPlanningSession.getTitle(),
                newPlanningSession.getDeckType().toString(),
                newLinkSession.getLink());
    }

    @Override
    public PlanningSessionResponse convert(PlanningPokerSessionDTO source) {
        final var newPlanningSession = source.getNewPlanningSession();
        final var newLinkSession = source.getNewLinkSession();

        return new PlanningSessionResponse(newPlanningSession.getId(),
                newPlanningSession.getTitle(),
                newPlanningSession.getDeckType().toString(),
                newLinkSession.getLink());
    }
}