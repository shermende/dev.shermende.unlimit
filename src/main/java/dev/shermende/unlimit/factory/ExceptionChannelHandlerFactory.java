package dev.shermende.unlimit.factory;

import dev.shermende.support.spring.factory.AbstractFactory;
import dev.shermende.unlimit.event.FileEvent;
import dev.shermende.unlimit.event.PayloadEvent;
import dev.shermende.unlimit.event.ReadlineEvent;
import dev.shermende.unlimit.handler.error.FileEventExceptionHandler;
import dev.shermende.unlimit.handler.error.PayloadExceptionHandler;
import dev.shermende.unlimit.handler.error.ReadlineEventExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.messaging.MessageHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ExceptionChannelHandlerFactory extends AbstractFactory<String, MessageHandler> {

    public ExceptionChannelHandlerFactory(
        BeanFactory beanFactory
    ) {
        super(beanFactory);
    }

    @Override
    protected void registration() {
        this.registry(FileEvent.class.getSimpleName(), FileEventExceptionHandler.class);
        this.registry(ReadlineEvent.class.getSimpleName(), ReadlineEventExceptionHandler.class);
        this.registry(PayloadEvent.class.getSimpleName(), PayloadExceptionHandler.class);
    }

}