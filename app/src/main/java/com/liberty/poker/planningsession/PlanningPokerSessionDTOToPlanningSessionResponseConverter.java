package com.liberty.poker.planningsession;

import com.liberty.poker.AbstractConverter;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.stereotype.Component;

@Component
public class PlanningPokerSessionDTOToPlanningSessionResponseConverter extends AbstractConverter<PlanningPokerSessionDTO, PlanningSessionResponse> {

    protected PlanningPokerSessionDTOToPlanningSessionResponseConverter(ConfigurableConversionService conversionService) {
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
