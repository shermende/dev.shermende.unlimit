package dev.shermende.unlimit.gateway.impl;

import dev.shermende.unlimit.event.FileEvent;
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
public class FileEventGateway implements Gateway<Boolean, FileEvent> {

    @Qualifier("eventChannel")
    private final MessageChannel channel;

    @Override
    public Boolean send(
        FileEvent payload
    ) {
        return channel.send(
            MessageBuilder.withPayload(payload)
                .setHeader(EventChannelHandler.HEADER, FileEvent.class.getSimpleName())
                .build()
        );
    }

}