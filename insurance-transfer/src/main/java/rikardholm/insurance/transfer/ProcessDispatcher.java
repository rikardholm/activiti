package rikardholm.insurance.transfer;

import org.activiti.engine.RuntimeService;
import rikardholm.insurance.application.messaging.InboxRepository;
import rikardholm.insurance.application.messaging.IncomingMessage;
import rikardholm.insurance.application.messaging.MessageEvent;
import rikardholm.insurance.application.messaging.MessageEventRepository;
import rikardholm.insurance.application.messaging.event.MessageHandledEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Predicates.instanceOf;
import static com.google.common.collect.Iterables.any;

public class ProcessDispatcher {

    public static final String INCOMING_MESSAGE_PROPERTY = "incomingMessage";
    private RuntimeService runtimeService;
    private InboxRepository inboxRepository;
    private MessageEventRepository messageEventRepository;
    private final Map<Class<? extends IncomingMessage>, String> processMap;

    public ProcessDispatcher(RuntimeService runtimeService, InboxRepository inboxRepository, MessageEventRepository messageEventRepository, Map<Class<? extends IncomingMessage>, String> processMap) {
        this.runtimeService = runtimeService;
        this.inboxRepository = inboxRepository;
        this.messageEventRepository = messageEventRepository;
        this.processMap = processMap;
    }

    public void pollInbox() {
        List<IncomingMessage> incomingMessages = inboxRepository.find(IncomingMessage.class);
        for (IncomingMessage incomingMessage : incomingMessages) {
            if (alreadyHandled(incomingMessage)) {
                continue;
            }
            startProcess(incomingMessage);
            messageEventRepository.save(new MessageHandledEvent(incomingMessage));
        }
    }

    private boolean alreadyHandled(IncomingMessage incomingMessage) {
        List<MessageEvent> messageEvents = messageEventRepository.findBy(incomingMessage);

        return any(messageEvents, instanceOf(MessageHandledEvent.class));
    }

    private void startProcess(IncomingMessage incomingMessage) {
        String processKey = processMap.get(incomingMessage.getClass());

        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put(
                INCOMING_MESSAGE_PROPERTY, incomingMessage);

        runtimeService.startProcessInstanceByKey(processKey, properties);
    }
}
