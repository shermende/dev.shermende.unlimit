package dev.shermende.unlimit.handler;

import dev.shermende.support.spring.factory.Factory;
import dev.shermende.unlimit.util.LogUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.handler.AbstractMessageHandler;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.support.ErrorMessage;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class ExceptionChannelHandler extends AbstractMessageHandler {

    private final Factory<String, MessageHandler> factory;

    @Override
    protected void handleMessageInternal(
        Message<?> message
    ) {
        log.debug("Exception event accepted to handle [{}]", LogUtil.sanitize(message));
        try {
            final ErrorMessage errorMessage = (ErrorMessage) message;
            final MessagingException exception = (MessagingException) errorMessage.getPayload();
            final Optional<String> keyOptional =
                Optional.ofNullable(exception.getFailedMessage())
                    .map(Message::getHeaders)
                    .map(headers -> headers.get(EventChannelHandler.HEADER))
                    .map(Object::toString)
                    .filter(factory::containsKey);

            // delegate exception
            if (keyOptional.isPresent()) {
                factory.getInstance(keyOptional.get()).handleMessage(message);
                return;
            }

            // log in general case
            log.error(exception.getMessage(), exception);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

}