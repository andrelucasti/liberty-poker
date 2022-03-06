package com.liberty.poker;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.ConfigurableConversionService;

import javax.annotation.PostConstruct;

public abstract class AbstractConverter<S, T> implements Converter<S, T> {

    private final ConfigurableConversionService conversionService;

    protected AbstractConverter(final ConfigurableConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @PostConstruct
    private void postConstruct() {
        conversionService.addConverter(this);
    }
}
