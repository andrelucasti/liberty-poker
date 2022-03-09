package com.liberty.poker.planningsession.converter;

import com.liberty.poker.AbstractConverter;
import com.liberty.poker.planningsession.PlanningSession;
import com.liberty.poker.planningsession.PlanningSessionEntity;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.stereotype.Component;

@Component
public class PlanningSessionToPlanningSessionEntityConverter extends AbstractConverter<PlanningSession, PlanningSessionEntity> {

    protected PlanningSessionToPlanningSessionEntityConverter(ConfigurableConversionService conversionService) {
        super(conversionService);
    }

    @Override
    public PlanningSessionEntity convert(final PlanningSession planningSession){
        return PlanningSessionEntity.of(planningSession.getTitle(), planningSession.getDeckType());
    }
}
