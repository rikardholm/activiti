package rikardholm.insurance.application.messaging.impl;

import rikardholm.insurance.application.messaging.Message;

import java.time.Instant;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

public class MessageImpl implements Message {
    private final UUID uuid;
    private final Instant receivedAt;
    private final String payLoad;

    public MessageImpl(UUID uuid, Instant receivedAt, String payLoad) {
        this.uuid = requireNonNull(uuid);
        this.receivedAt = requireNonNull(receivedAt);
        this.payLoad = requireNonNull(payLoad);
    }

    @Override
    public UUID getUuid() {
        return uuid;
    }

    @Override
    public Instant getReceivedAt() {
        return receivedAt;
    }

    @Override
    public String getPayload() {
        return payLoad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MessageImpl message = (MessageImpl) o;

        return uuid.equals(message.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

    @Override
    public String toString() {
        return "MessageImpl{" +
                uuid +
                ", " + receivedAt +
                '}';
    }
}
