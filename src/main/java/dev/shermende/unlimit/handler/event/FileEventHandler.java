package dev.shermende.unlimit.handler.event;

import dev.shermende.unlimit.event.FileEvent;
import dev.shermende.unlimit.event.ReadlineEvent;
import dev.shermende.unlimit.gateway.Gateway;
import dev.shermende.unlimit.handler.support.AbstractValidationMessageHandler;
import dev.shermende.unlimit.util.LogUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Component
public class FileEventHandler extends AbstractValidationMessageHandler {

    private final Gateway<Boolean, ReadlineEvent> gateway;

    public FileEventHandler(
        @Qualifier("fileEventValidator") Validator validator,
        Gateway<Boolean, ReadlineEvent> gateway
    ) {
        super(validator);
        this.gateway = gateway;
    }

    @Override
    @SneakyThrows
    protected void handleMessageInternal(
        Message<?> message
    ) {
        final AtomicLong counter = new AtomicLong(1L);
        final FileEvent event = (FileEvent) message.getPayload();
        final String extension = FilenameUtils.getExtension(event.getFilename());
        try (BufferedReader br = new BufferedReader(new FileReader(event.getFilename()))) {
            for (String line; (line = br.readLine()) != null; )
                gateway.send(getBuild(counter, event.getFilename(), extension, line));
        }
        log.debug("FileEvent handled [{}]", LogUtil.sanitize(message));
    }

    private ReadlineEvent getBuild(
        AtomicLong counter,
        String filename,
        String extension,
        String line
    ) {
        return ReadlineEvent.builder()
            .filename(filename)
            .extension(extension)
            .line(counter.getAndIncrement())
            .payload(line)
            .build();
    }

}