package dev.shermende.unlimit.handler.event;

import dev.shermende.unlimit.event.FileEvent;
import dev.shermende.unlimit.gateway.impl.ReadlineEventGateway;
import dev.shermende.unlimit.validator.FileEventValidator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;


@Slf4j
@ExtendWith(MockitoExtension.class)
class FileEventHandlerTest {

    @InjectMocks
    private FileEventHandler handler;
    @Mock
    private FileEventValidator validator;
    @Mock
    private ReadlineEventGateway gateway;

    @Test
    void handleMessageInternal() {
        doNothing().when(validator).validate(any(), any());
        given(validator.supports(any())).willReturn(true);
        given(gateway.send(any())).willReturn(true);

        final Message<FileEvent> message =
            MessageBuilder
                .withPayload(FileEvent.builder().filename("resource:source/data.csv").build())
                .build();
        handler.handleMessage(message);

        then(gateway).should(times(1000)).send(any());
    }

}