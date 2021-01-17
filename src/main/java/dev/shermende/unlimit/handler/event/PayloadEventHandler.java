package dev.shermende.unlimit.handler.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.shermende.unlimit.handler.support.AbstractValidationMessageHandler;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;

@Slf4j
@Component
public class PayloadEventHandler extends AbstractValidationMessageHandler {

    private final ObjectMapper objectMapper;

    public PayloadEventHandler(
        @Qualifier("payloadEventValidator") Validator validator,
        @Qualifier("jsonObjectMapper") ObjectMapper objectMapper
    ) {
        super(validator);
        this.objectMapper = objectMapper;
    }

    @Override
    @SneakyThrows
    protected void handleMessageInternal(
        Message<?> message
    ) {
        System.out.println(objectMapper.writeValueAsString(message.getPayload()));
    }

}