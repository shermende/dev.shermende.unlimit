package dev.shermende.unlimit.configuration.properties;

import lombok.Data;

import java.io.Serializable;

@Data
public class UnlimitProperties implements Serializable {
    private int concurrency = 33;
}
