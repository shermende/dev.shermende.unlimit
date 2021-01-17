package dev.shermende.unlimit.handler;

import dev.shermende.unlimit.factory.ExceptionChannelHandlerFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.integration.handler.AbstractMessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.messaging.support.MessageBuilder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@Slf4j
@ExtendWith(MockitoExtension.class)
class ExceptionChannelHandlerTest {

    @InjectMocks
    private ExceptionChannelHandler handler;
    @Mock
    private ExceptionChannelHandlerFactory factory;
    @Mock
    private AbstractMessageHandler targetHandler;

    @Test
    void handleMessageInternal() {
        given(factory.containsKey("key")).willReturn(true);
        given(factory.getInstance("key")).willReturn(targetHandler);

        final ErrorMessage errorMessage =
            new ErrorMessage(
                new MessagingException( MessageBuilder.withPayload(new Object())
                    .setHeader(EventChannelHandler.HEADER, "key")
                    .build(), new IllegalArgumentException()));

        handler.handleMessage(errorMessage);

        then(factory).should(times(1)).getInstance("key");
        then(targetHandler).should(times(1)).handleMessage(any());
    }

    @Test
    void handleMessageInternalEmptyKey() {
        given(factory.containsKey("key")).willReturn(false);

        final ErrorMessage errorMessage =
            new ErrorMessage(
                new MessagingException( MessageBuilder.withPayload(new Object())
                    .setHeader(EventChannelHandler.HEADER, "key")
                    .build(), new IllegalArgumentException()));

        handler.handleMessage(errorMessage);

        then(factory).should(times(0)).getInstance("key");
        then(targetHandler).should(times(0)).handleMessage(any());
    }

}