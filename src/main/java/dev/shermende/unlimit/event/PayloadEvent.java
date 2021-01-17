package dev.shermende.unlimit.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PayloadEvent {
    @NotEmpty
    private String filename;
    @NotNull
    private Long line;
    @NotEmpty
    private String result;
    private String orderId;
    private Double amount;
    private String currency;
    private String comment;
}