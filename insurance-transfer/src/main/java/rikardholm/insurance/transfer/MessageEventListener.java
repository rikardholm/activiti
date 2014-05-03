package rikardholm.insurance.transfer;

import rikardholm.insurance.messaging.message.IncomingMessage;

public interface MessageEventListener {
    void newMessage(IncomingMessage incomingMessage);
}
