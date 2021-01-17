package dev.shermende.unlimit.handler.event;

import dev.shermende.unlimit.configuration.ApplicationConfiguration;
import dev.shermende.unlimit.configuration.IntegrationConfiguration;
import dev.shermende.unlimit.event.PayloadEvent;
import dev.shermende.unlimit.factory.EventChannelHandlerFactory;
import dev.shermende.unlimit.factory.ExceptionChannelHandlerFactory;
import dev.shermende.unlimit.handler.EventChannelHandler;
import dev.shermende.unlimit.handler.error.PayloadExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.timeout;

@Slf4j
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
    ApplicationConfiguration.class,
    IntegrationConfiguration.class,
    EventChannelHandler.class,
    EventChannelHandlerFactory.class,
    ExceptionChannelHandlerFactory.class,
})
class PayloadEventHandlerITTest {

    @SpyBean
    @Autowired
    @Qualifier("eventChannel")
    private SubscribableChannel channel;

    @MockBean
    private PayloadEventHandler channelHandler;

    @MockBean
    private PayloadExceptionHandler channelErrorHandler;

    @Captor
    private ArgumentCaptor<Message<?>> captor;

    @Test
    void handleMessageInternalException() {
        final Object object = new Object();

        doThrow(new IllegalArgumentException()).when(channelHandler).handleMessage(any());

        channel.send(
            MessageBuilder.withPayload(object)
                .setHeader(EventChannelHandler.HEADER, PayloadEvent.class.getSimpleName())
                .build()
        );

        then(channelErrorHandler).should(timeout(1000L)).handleMessage(captor.capture());

        final ErrorMessage errorMessage = (ErrorMessage) captor.getValue();
        final MessagingException exception = (MessagingException) errorMessage.getPayload();

        Assertions.assertEquals(object, Objects.requireNonNull(exception.getFailedMessage()).getPayload());
        Assertions.assertEquals(PayloadEvent.class.getSimpleName(), exception.getFailedMessage().getHeaders().get(EventChannelHandler.HEADER));
    }

}