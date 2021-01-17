package dev.shermende.unlimit.event;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
public class FileEvent {
    @NotEmpty
    private String filename;
}