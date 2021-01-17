package dev.shermende.unlimit.handler.event;

import dev.shermende.support.spring.factory.Factory;
import dev.shermende.unlimit.event.PayloadEvent;
import dev.shermende.unlimit.event.ReadlineEvent;
import dev.shermende.unlimit.gateway.Gateway;
import dev.shermende.unlimit.util.LogUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.integration.handler.AbstractMessageHandler;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReadlineEventHandler extends AbstractMessageHandler {

    private final Gateway<Boolean, PayloadEvent> gateway;
    private final Factory<String, Converter<ReadlineEvent, PayloadEvent>> factory;

    @Override
    protected void handleMessageInternal(
        Message<?> message
    ) {
        final ReadlineEvent readlineEvent = (ReadlineEvent) message.getPayload();
        gateway.send(factory.getInstance(readlineEvent.getExtension()).convert(readlineEvent));
        log.debug("ReadlineEvent handled [{}]", LogUtil.sanitize(message));
    }

}