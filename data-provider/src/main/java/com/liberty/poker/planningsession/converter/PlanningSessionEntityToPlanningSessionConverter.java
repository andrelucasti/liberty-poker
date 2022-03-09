package com.liberty.poker.planningsession.converter;

import com.liberty.poker.AbstractConverter;
import com.liberty.poker.planningsession.PlanningSession;
import com.liberty.poker.planningsession.PlanningSessionEntity;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.stereotype.Component;

@Component
public class PlanningSessionEntityToPlanningSessionConverter extends AbstractConverter<PlanningSessionEntity, PlanningSession> {

    protected PlanningSessionEntityToPlanningSessionConverter(ConfigurableConversionService conversionService) {
        super(conversionService);
    }

    @Override
    public PlanningSession convert(final PlanningSessionEntity planningSessionEntity){
        return new PlanningSession(planningSessionEntity.getUuid(), planningSessionEntity.getTitle(), planningSessionEntity.getDeckType());
    }
}
