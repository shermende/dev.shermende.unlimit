package dev.shermende.unlimit;

import dev.shermende.unlimit.event.FileEvent;
import dev.shermende.unlimit.gateway.Gateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.stream.Stream;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class UnlimitApplication implements CommandLineRunner {

    public static void main(String... args) {
        SpringApplication.run(UnlimitApplication.class, args);
    }

    @Qualifier("fileEventGateway")
    private final Gateway<Boolean, FileEvent> gateway;

    @Override
    public void run(String... args) {
        Stream.of(args).parallel().map(s -> FileEvent.builder().filename(s).build()).forEach(gateway::send);
    }

}