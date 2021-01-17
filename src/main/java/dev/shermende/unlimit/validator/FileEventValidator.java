package dev.shermende.unlimit.validator;

import dev.shermende.unlimit.event.FileEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.validation.Validation;

@Slf4j
@Component
public class FileEventValidator implements Validator {

    @Override
    public boolean supports(
        Class<?> aClass
    ) {
        return aClass.isAssignableFrom(FileEvent.class);
    }

    @Override
    public void validate(
        Object o,
        Errors errors
    ) {
        Validation.buildDefaultValidatorFactory().getValidator().validate(o)
            .stream()
            .map(violation -> String.format("%s: %s", violation.getPropertyPath(), violation.getMessage()))
            .forEach(errors::reject);
    }

}
