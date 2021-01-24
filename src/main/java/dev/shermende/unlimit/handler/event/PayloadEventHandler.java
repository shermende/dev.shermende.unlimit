package dev.shermende.unlimit.handler.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.shermende.unlimit.handler.support.AbstractValidationMessageHandler;
import dev.shermende.unlimit.service.ReadlineLifeCycleService;
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
    private final ReadlineLifeCycleService readlineLifeCycleService;

    public PayloadEventHandler(
        @Qualifier("payloadEventValidator") Validator validator,
        @Qualifier("jsonObjectMapper") ObjectMapper objectMapper,
        @Qualifier("readlineLifeCycleServiceImpl") ReadlineLifeCycleService readlineLifeCycleService
    ) {
        super(validator);
        this.objectMapper = objectMapper;
        this.readlineLifeCycleService = readlineLifeCycleService;
    }

    @Override
    @SneakyThrows
    protected void handleMessageInternal(
        Message<?> message
    ) {
        System.out.println(objectMapper.writeValueAsString(message.getPayload()));
        readlineLifeCycleService.finish();
    }

}