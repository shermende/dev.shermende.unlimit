package dev.shermende.unlimit.handler.event;

import dev.shermende.unlimit.util.LogUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.integration.handler.AbstractMessageHandler;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ShutdownEventHandler extends AbstractMessageHandler {

    private final ApplicationContext context;

    @Override
    protected void handleMessageInternal(
        Message<?> message
    ) {
        log.debug("ShutdownEvent accepted [{}]", LogUtil.sanitize(message));
        System.exit(SpringApplication.exit(context));
    }

}