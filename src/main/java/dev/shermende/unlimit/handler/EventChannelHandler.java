package dev.shermende.unlimit.handler;

import dev.shermende.support.spring.factory.Factory;
import dev.shermende.unlimit.util.LogUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.handler.AbstractMessageHandler;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventChannelHandler extends AbstractMessageHandler {
    public static final String HEADER = "X-HEADER";

    @Qualifier("eventChannelHandlerFactory")
    private final Factory<String, AbstractMessageHandler> factory;

    @Override
    protected void handleMessageInternal(
        Message<?> message
    ) {
        log.debug("Event accepted to handle [{}]", LogUtil.sanitize(message));
        factory.getInstance(getEvent(message)).handleMessage(message);
    }

    private String getEvent(
        Message<?> message
    ) {
        return Optional.ofNullable(message.getHeaders().get(HEADER)).map(String::valueOf)
            .orElseThrow(() -> new IllegalArgumentException(String.format("Message not contain X-HEADER: [%s]", message)));
    }

}