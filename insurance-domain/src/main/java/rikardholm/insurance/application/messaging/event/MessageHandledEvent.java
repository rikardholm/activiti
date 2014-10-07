package rikardholm.insurance.application.messaging.event;

import rikardholm.insurance.application.messaging.Message;
import rikardholm.insurance.application.messaging.MessageEvent;

import java.util.UUID;

public class MessageHandledEvent implements MessageEvent {

    private Message message;

    public MessageHandledEvent(Message message) {
        this.message = message;
    }

    @Override
    public Message getMessage() {
        return message;
    }

    @Override
    public UUID getUUID() {
        return null;
    }

    @Override
    public String getEvent() {
        return null;
    }
}
