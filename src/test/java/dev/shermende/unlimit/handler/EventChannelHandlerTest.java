package dev.shermende.unlimit.handler;

import dev.shermende.unlimit.factory.EventChannelHandlerFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.integration.handler.AbstractMessageHandler;
import org.springframework.messaging.MessageHandlingException;
import org.springframework.messaging.support.MessageBuilder;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@Slf4j
@ExtendWith(MockitoExtension.class)
class EventChannelHandlerTest {

    @InjectMocks
    private EventChannelHandler handler;
    @Mock
    private EventChannelHandlerFactory factory;
    @Mock
    private AbstractMessageHandler targetHandler;

    @Test
    void handleMessageInternal() {
        given(factory.getInstance("key")).willReturn(targetHandler);

        handler.handleMessage(
            MessageBuilder.withPayload(new Object())
                .setHeader(EventChannelHandler.HEADER, "key")
                .build()
        );

        then(factory).should(times(1)).getInstance("key");
        then(targetHandler).should(times(1)).handleMessage(any());
    }

    @Test
    void handleMessageInternalEmptyKey() {
        final Executable executable = () -> handler.handleMessage(
            MessageBuilder.withPayload(new Object())
                .build()
        );

        final MessageHandlingException exception = assertThrows(MessageHandlingException.class, executable);
        Assertions.assertEquals(IllegalArgumentException.class, exception.getCause().getClass());
    }

}