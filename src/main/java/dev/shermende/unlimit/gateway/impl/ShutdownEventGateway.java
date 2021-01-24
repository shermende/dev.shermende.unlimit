package dev.shermende.unlimit.gateway.impl;

import dev.shermende.unlimit.event.ShutdownEvent;
import dev.shermende.unlimit.gateway.Gateway;
import dev.shermende.unlimit.handler.EventChannelHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ShutdownEventGateway implements Gateway<Boolean, ShutdownEvent> {

    @Qualifier("eventChannel")
    private final MessageChannel channel;

    @Override
    public Boolean send(
        ShutdownEvent payload
    ) {
        return channel.send(
            MessageBuilder.withPayload(payload)
                .setHeader(EventChannelHandler.HEADER, ShutdownEvent.class.getSimpleName())
                .build()
        );
    }

}