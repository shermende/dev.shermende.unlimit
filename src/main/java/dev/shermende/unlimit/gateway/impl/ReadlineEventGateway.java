package dev.shermende.unlimit.gateway.impl;

import dev.shermende.unlimit.event.ReadlineEvent;
import dev.shermende.unlimit.gateway.Gateway;
import dev.shermende.unlimit.handler.EventChannelHandler;
import dev.shermende.unlimit.service.ReadlineLifeCycleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReadlineEventGateway implements Gateway<Boolean, ReadlineEvent> {

    @Qualifier("eventChannel")
    private final MessageChannel channel;
    @Qualifier("readlineLifeCycleServiceImpl")
    private final ReadlineLifeCycleService readlineLifeCycleService;

    @Override
    public Boolean send(
        ReadlineEvent payload
    ) {
        readlineLifeCycleService.start();
        return channel.send(
            MessageBuilder.withPayload(payload)
                .setHeader(EventChannelHandler.HEADER, ReadlineEvent.class.getSimpleName())
                .build()
        );
    }

}