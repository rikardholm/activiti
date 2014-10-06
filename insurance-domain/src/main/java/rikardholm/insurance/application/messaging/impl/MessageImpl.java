package rikardholm.insurance.application.messaging.impl;

import rikardholm.insurance.application.messaging.Message;

import java.time.Instant;
import java.util.UUID;

public class MessageImpl implements Message {
    private final UUID id;
    private final Instant receivedAt;
    private final String type;
    private final String payLoad;

    public MessageImpl(UUID id, Instant receivedAt, String type, String payLoad) {
        this.id = id;
        this.receivedAt = receivedAt;
        this.type = type;
        this.payLoad = payLoad;
    }

    @Override
    public UUID getUuid() {
        return id;
    }

    @Override
    public Instant getReceivedAt() {
        return receivedAt;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getPayload() {
        return payLoad;
    }
}
