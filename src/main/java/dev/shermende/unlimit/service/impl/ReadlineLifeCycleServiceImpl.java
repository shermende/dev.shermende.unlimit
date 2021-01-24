package dev.shermende.unlimit.service.impl;

import dev.shermende.unlimit.event.ShutdownEvent;
import dev.shermende.unlimit.gateway.Gateway;
import dev.shermende.unlimit.service.ReadlineLifeCycleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReadlineLifeCycleServiceImpl implements ReadlineLifeCycleService {

    @Qualifier("shutdownEventGateway")
    private final Gateway<Boolean, ShutdownEvent> gateway;
    private final AtomicInteger started = new AtomicInteger();
    private final AtomicInteger finished = new AtomicInteger();

    @Override
    public void start() {
        started.incrementAndGet();
    }

    @Override
    public void finish() {
        finished.incrementAndGet();
        if (started.get() != finished.get()) return;
        gateway.send(ShutdownEvent.builder().started(started.get()).finished(finished.get()).build());
    }

}