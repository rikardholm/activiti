package rikardholm.insurance.application.messaging.impl;

import rikardholm.insurance.application.messaging.Message;
import rikardholm.insurance.application.messaging.MessageEvent;

import java.util.UUID;

public class MessageEventImpl implements MessageEvent {

    private final UUID uuid;
    private final String event;

    public MessageEventImpl(UUID uuid, String event) {
        this.uuid = uuid;
        this.event = event;
    }

    @Override
    public Message getMessage() {
        return null;
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public String getEvent() {
        return event;
    }
}
