package rikardholm.insurance.transfer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rikardholm.insurance.messaging.InboxRepository;
import rikardholm.insurance.messaging.message.IncomingMessage;

import java.util.List;

public class MessageHandler {
    private static Logger log = LoggerFactory.getLogger(MessageHandler.class);

    private InboxRepository inboxRepository;
    private List<Listener> listeners;

    public MessageHandler(InboxRepository inboxRepository, List<Listener> listeners) {
        this.inboxRepository = inboxRepository;
        this.listeners = listeners;
    }


    public void start() {
        log.info("Starting...");
    }

    public void stop() {
        log.info("Stoping...");
    }

    interface Listener {
        void newMessage(IncomingMessage incomingMessage);
    }
}
