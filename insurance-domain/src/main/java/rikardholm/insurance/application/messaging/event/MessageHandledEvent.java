package rikardholm.insurance.application.messaging.event;

import rikardholm.insurance.application.messaging.Message;
import rikardholm.insurance.application.messaging.MessageEvent;

public class MessageHandledEvent implements MessageEvent {

    private Message message;

    public MessageHandledEvent(Message message) {
        this.message = message;
    }

    @Override
    public Message getMessage() {
        return message;
    }
}
