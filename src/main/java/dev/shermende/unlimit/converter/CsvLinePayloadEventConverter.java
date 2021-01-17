package dev.shermende.unlimit.converter;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
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
public class CsvLinePayloadEventConverter implements Converter<ReadlineEvent, PayloadEvent> {
    private static final String OK = "OK";

    private final CsvSchema schema;
    private final CsvMapper objectMapper;

    @Override
    @SneakyThrows
    public PayloadEvent convert(
        ReadlineEvent readline
    ) {
        final PayloadEvent payloadEvent =
            objectMapper.readerWithSchemaFor(PayloadEvent.class).with(schema).readValue(readline.getPayload());
        return payloadEvent
            .setResult(OK)
            .setFilename(readline.getFilename())
            .setLine(readline.getLine());
    }

}