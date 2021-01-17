package dev.shermende.unlimit.event;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class ReadlineEvent {
    @NotEmpty
    private String filename;
    @NotEmpty
    private String extension;
    @NotNull
    private Long line;
    @NotEmpty
    private String payload;
}