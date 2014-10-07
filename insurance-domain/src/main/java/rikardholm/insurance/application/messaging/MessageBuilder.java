package rikardholm.insurance.application.messaging;

import rikardholm.insurance.application.messaging.impl.MessageImpl;

import java.time.Instant;
import java.util.UUID;

public class MessageBuilder {

    private UUID id = UUID.randomUUID();
    private Instant receivedAt;
    private String payload;

    private MessageBuilder() {
    }

    public static MessageBuilder aMessage() {
        return new MessageBuilder();
    }

    public MessageBuilder withUUID(UUID id) {
        this.id = id;
        return this;
    }

    public MessageBuilder receivedAt(Instant instant) {
        receivedAt = instant;
        return this;
    }

    public MessageBuilder payload(String payload) {
        this.payload = payload;
        return this;
    }

    public Message build() {
        return new MessageImpl(id,receivedAt, payload);
    }
}
