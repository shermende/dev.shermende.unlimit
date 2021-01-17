package dev.shermende.unlimit.handler.support;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.handler.AbstractMessageHandler;
import org.springframework.messaging.Message;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractValidationMessageHandler extends AbstractMessageHandler {

    private final Validator validator;

    @Override
    public void handleMessage(Message<?> message) {
        Optional.of(validate(message)).filter(Errors::hasErrors)
            .ifPresent(bindingResult -> {
                throw new IllegalArgumentException(bindingResult.getAllErrors().toString());
            });
        super.handleMessage(message);
    }

    private BindingResult validate(
        Message<?> message
    ) {
        final DataBinder dataBinder = new DataBinder(message.getPayload());
        dataBinder.addValidators(validator);
        dataBinder.validate();
        return dataBinder.getBindingResult();
    }
}