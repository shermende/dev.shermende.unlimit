package dev.shermende.unlimit.handler.event;

import dev.shermende.support.spring.factory.Factory;
import dev.shermende.unlimit.event.PayloadEvent;
import dev.shermende.unlimit.event.ReadlineEvent;
import dev.shermende.unlimit.gateway.Gateway;
import dev.shermende.unlimit.handler.support.AbstractValidationMessageHandler;
import dev.shermende.unlimit.util.LogUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.converter.Converter;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;

@Slf4j
@Component
public class ReadlineEventHandler extends AbstractValidationMessageHandler {

    private final Gateway<Boolean, PayloadEvent> gateway;
    private final Factory<String, Converter<ReadlineEvent, PayloadEvent>> factory;

    public ReadlineEventHandler(
        @Qualifier("readlineEventValidator") Validator validator,
        @Qualifier("payloadEventGateway") Gateway<Boolean, PayloadEvent> gateway,
        @Qualifier("readlineEventConverterFactory") Factory<String, Converter<ReadlineEvent, PayloadEvent>> factory
    ) {
        super(validator);
        this.gateway = gateway;
        this.factory = factory;
    }

    @Override
    protected void handleMessageInternal(
        Message<?> message
    ) {
        final ReadlineEvent readlineEvent = (ReadlineEvent) message.getPayload();
        gateway.send(factory.getInstance(readlineEvent.getExtension()).convert(readlineEvent));
        log.debug("ReadlineEvent handled [{}]", LogUtil.sanitize(message));
    }

}