package dev.shermende.unlimit.handler.error;

import dev.shermende.unlimit.event.PayloadEvent;
import dev.shermende.unlimit.event.ReadlineEvent;
import dev.shermende.unlimit.gateway.Gateway;
import dev.shermende.unlimit.util.LogUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.handler.AbstractMessageHandler;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReadlineEventExceptionHandler extends AbstractMessageHandler {

    private final Gateway<Boolean, PayloadEvent> gateway;

    @Override
    protected void handleMessageInternal(
        Message<?> exceptionMessage
    ) {
        // error-message-holder
        final ErrorMessage errorMessage = (ErrorMessage) exceptionMessage;
        final MessagingException messagingException = (MessagingException) errorMessage.getPayload();
        final Throwable throwable = messagingException.getCause();
        final Message<?> message = messagingException.getFailedMessage();
        //
        final ReadlineEvent readlineEvent = (ReadlineEvent) Objects.requireNonNull(message).getPayload();
        gateway.send(
            new PayloadEvent()
                .setResult(LogUtil.sanitize(throwable.getMessage()))
                .setFilename(readlineEvent.getFilename())
                .setLine(readlineEvent.getLine())
        );
        log.debug("Exception ReadlineEvent handled [{}]", LogUtil.sanitize(message));
    }

}