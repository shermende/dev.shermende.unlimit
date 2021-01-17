package dev.shermende.unlimit.factory;

import dev.shermende.support.spring.factory.AbstractFactory;
import dev.shermende.unlimit.converter.CsvLinePayloadEventConverter;
import dev.shermende.unlimit.converter.JsonLinePayloadEventConverter;
import dev.shermende.unlimit.event.PayloadEvent;
import dev.shermende.unlimit.event.ReadlineEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ReadlineEventConverterFactory extends AbstractFactory<String, Converter<ReadlineEvent, PayloadEvent>> {

    public ReadlineEventConverterFactory(
        BeanFactory beanFactory
    ) {
        super(beanFactory);
    }

    @Override
    protected void registration() {
        this.registry("csv", CsvLinePayloadEventConverter.class);
        this.registry("json", JsonLinePayloadEventConverter.class);
    }
}
