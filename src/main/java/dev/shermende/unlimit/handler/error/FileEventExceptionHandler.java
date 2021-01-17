package dev.shermende.unlimit.handler.error;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.handler.AbstractMessageHandler;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class FileEventExceptionHandler extends AbstractMessageHandler {

    @Override
    protected void handleMessageInternal(
        Message<?> exceptionMessage
    ) {
        // error-message-holder
        final ErrorMessage errorMessage = (ErrorMessage) exceptionMessage;
        final MessagingException messagingException = (MessagingException) errorMessage.getPayload();
        final Throwable throwable = messagingException.getCause();
        //
        log.warn(throwable.getMessage(), throwable);
    }

}