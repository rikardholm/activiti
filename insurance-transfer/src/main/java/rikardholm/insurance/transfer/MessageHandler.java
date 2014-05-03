package rikardholm.insurance.transfer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rikardholm.insurance.messaging.InboxRepository;

import java.util.List;

public class MessageHandler {
    private static Logger log = LoggerFactory.getLogger(MessageHandler.class);

    private InboxRepository inboxRepository;
    private List<MessageEventListener> listeners;

    public MessageHandler(InboxRepository inboxRepository, List<MessageEventListener> listeners) {
        this.inboxRepository = inboxRepository;
        this.listeners = listeners;
    }


    public void start() {
        log.info("Starting...");
    }

    public void stop() {
        log.info("Stoping...");
    }
}
