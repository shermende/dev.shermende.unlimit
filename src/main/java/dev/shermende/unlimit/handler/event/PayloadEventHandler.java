package dev.shermende.unlimit.handler.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.handler.AbstractMessageHandler;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PayloadEventHandler extends AbstractMessageHandler {

    @Qualifier("jsonObjectMapper")
    private final ObjectMapper objectMapper;

    @Override
    @SneakyThrows
    protected void handleMessageInternal(
        Message<?> message
    ) {
        System.out.println(objectMapper.writeValueAsString(message.getPayload()));
    }

}