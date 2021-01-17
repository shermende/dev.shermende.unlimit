package dev.shermende.unlimit.configuration;

import dev.shermende.support.spring.factory.Factory;
import dev.shermende.unlimit.configuration.properties.UnlimitProperties;
import dev.shermende.unlimit.handler.EventChannelHandler;
import dev.shermende.unlimit.handler.ExceptionChannelHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.SubscribableChannel;

import java.util.concurrent.Executors;

@Configuration
@EnableIntegration
@RequiredArgsConstructor
public class IntegrationConfiguration {

    @Bean
    public SubscribableChannel eventChannel(
        UnlimitProperties properties
    ) {
        return MessageChannels.executor(Executors.newFixedThreadPool(properties.getConcurrency())).get();
    }

    @Bean
    public IntegrationFlow systemChannelFlow(
        @Qualifier("eventChannel") SubscribableChannel channel,
        EventChannelHandler handler
    ) {
        return IntegrationFlows.from(channel)
            .handle(handler)
            .get();
    }

    @Bean
    public ExceptionChannelHandler handler(
        @Qualifier("exceptionChannelHandlerFactory") Factory<String, MessageHandler> factory
    ) {
        final ExceptionChannelHandler handler = new ExceptionChannelHandler(factory);
        handler.setLoggingEnabled(false);
        return handler;
    }

    @Bean
    public IntegrationFlow errorChannelFlow(
        @Qualifier("errorChannel") SubscribableChannel channel,
        ExceptionChannelHandler handler
    ) {
        return IntegrationFlows.from(channel)
            .handle(handler)
            .get();
    }

}
