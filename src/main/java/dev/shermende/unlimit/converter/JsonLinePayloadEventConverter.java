package dev.shermende.unlimit.converter;

import com.fasterxml.jackson.databind.json.JsonMapper;
import dev.shermende.unlimit.event.PayloadEvent;
import dev.shermende.unlimit.event.ReadlineEvent;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JsonLinePayloadEventConverter implements Converter<ReadlineEvent, PayloadEvent> {
    private static final String OK = "OK";

    private final JsonMapper objectMapper;

    @Override
    @SneakyThrows
    public PayloadEvent convert(
        ReadlineEvent readline
    ) {
        final PayloadEvent payloadEvent =
            objectMapper.readValue(readline.getPayload(), PayloadEvent.class);
        return payloadEvent
            .setResult(OK)
            .setFilename(readline.getFilename())
            .setLine(readline.getLine());
    }

}