package com.liberty.poker.planningsession;

import com.liberty.poker.AbstractConverter;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.stereotype.Component;

import static com.liberty.poker.planningsession.PlanningSession.*;

@Component
public class PlanningSessionRequestToPlanningSessionConverter extends AbstractConverter<PlanningSessionRequest, PlanningSession> {

    protected PlanningSessionRequestToPlanningSessionConverter(ConfigurableConversionService conversionService) {
        super(conversionService);
    }

    @Deprecated
    public PlanningSession converter(final PlanningSessionRequest planningSessionRequest){
        return new PlanningSession(planningSessionRequest.getTitle(), DeckType.valueOf(planningSessionRequest.getDeckType()));
    }

    @Override
    public PlanningSession convert(PlanningSessionRequest source) {
        return new PlanningSession(source.getTitle(), DeckType.valueOf(source.getDeckType()));
    }
}
