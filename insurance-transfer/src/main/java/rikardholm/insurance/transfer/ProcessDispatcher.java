package rikardholm.insurance.transfer;

import org.activiti.engine.RuntimeService;
import rikardholm.insurance.application.messaging.InboxRepository;
import rikardholm.insurance.application.messaging.IncomingMessage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProcessDispatcher {

    public static final String INCOMING_MESSAGE_PROPERTY = "incomingMessage";
    private RuntimeService runtimeService;
    private InboxRepository inboxRepository;
    private final Map<Class<? extends IncomingMessage>, String> processMap;

    public ProcessDispatcher(RuntimeService runtimeService, InboxRepository inboxRepository, Map<Class<? extends IncomingMessage>, String> processMap) {
        this.runtimeService = runtimeService;
        this.inboxRepository = inboxRepository;
        this.processMap = processMap;
    }

    public void pollInbox() {
        List<IncomingMessage> incomingMessages = inboxRepository.find(IncomingMessage.class);
        for (IncomingMessage incomingMessage : incomingMessages) {
            startProcess(incomingMessage);
        }
    }

    private void startProcess(IncomingMessage incomingMessage) {
        String processKey = processMap.get(incomingMessage.getClass());

        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put(
                INCOMING_MESSAGE_PROPERTY, incomingMessage);

        runtimeService.startProcessInstanceByKey(processKey, properties);
    }
}
