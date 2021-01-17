package dev.shermende.unlimit.factory;

import dev.shermende.support.spring.factory.AbstractFactory;
import dev.shermende.unlimit.event.FileEvent;
import dev.shermende.unlimit.event.PayloadEvent;
import dev.shermende.unlimit.event.ReadlineEvent;
import dev.shermende.unlimit.handler.event.FileEventHandler;
import dev.shermende.unlimit.handler.event.PayloadEventHandler;
import dev.shermende.unlimit.handler.event.ReadlineEventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.integration.handler.AbstractMessageHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EventChannelHandlerFactory extends AbstractFactory<String, AbstractMessageHandler> {

    public EventChannelHandlerFactory(
        BeanFactory beanFactory
    ) {
        super(beanFactory);
    }

    @Override
    protected void registration() {
        this.registry(FileEvent.class.getSimpleName(), FileEventHandler.class);
        this.registry(ReadlineEvent.class.getSimpleName(), ReadlineEventHandler.class);
        this.registry(PayloadEvent.class.getSimpleName(), PayloadEventHandler.class);
    }

}