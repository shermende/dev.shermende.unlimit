package dev.shermende.unlimit.gateway;

public interface Gateway<O, I> {
    O send(I payload);
}