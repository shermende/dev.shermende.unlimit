package dev.shermende.unlimit.event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShutdownEvent {
    private Integer started;
    private Integer finished;
}