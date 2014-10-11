package rikardholm.insurance.transfer;

import org.activiti.engine.RuntimeService;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rikardholm.insurance.application.messaging.Message;
import rikardholm.insurance.application.messaging.MessageEvent;
import rikardholm.insurance.application.messaging.MessageEventRepository;
import rikardholm.insurance.application.messaging.MessageRepository;
import rikardholm.insurance.application.messaging.impl.MessageEventImpl;

import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProcessDispatcher {
    public static final Logger log = LoggerFactory.getLogger(ProcessDispatcher.class);

    private static final String MESSAGE = "message";
    private static final String MESSAGE_PAYLOAD = "messagePayload";
    private RuntimeService runtimeService;
    private MessageRepository messageRepository;
    private MessageEventRepository messageEventRepository;
    private final Map<Class<Message>, String> processMap;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public ProcessDispatcher(RuntimeService runtimeService, MessageRepository messageRepository, MessageEventRepository messageEventRepository, Map<Class<Message>, String> processMap) {
        this.runtimeService = runtimeService;
        this.messageRepository = messageRepository;
        this.messageEventRepository = messageEventRepository;
        this.processMap = processMap;
    }

    public void poll() {
        List<Message> messages = messageRepository.receivedAfter(Instant.parse("2014-01-01T13:00:00Z"));
        for (Message message : messages) {
            if (alreadyHandled(message)) {
                continue;
            }
            startProcess(message);
            messageEventRepository.save(new MessageEventImpl(message.getUuid(), "handled"));
        }
    }

    private boolean alreadyHandled(Message message) {
        List<MessageEvent> messageEvents = messageEventRepository.findByUUID(message.getUuid());

        return !messageEvents.isEmpty();
    }

    private void startProcess(Message message) {
        String processKey = "";

        for (Map.Entry<Class<Message>, String> entry : processMap.entrySet()) {
            if (entry.getKey().isAssignableFrom(message.getClass())) {
                processKey = entry.getValue();
            }
        }

        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put(MESSAGE, message);
        try {
            properties.put(MESSAGE_PAYLOAD, objectMapper.readValue(message.getPayload(), Map.class));
        } catch (IOException e) {
            log.warn("Could not read json from payload for message " + message.getUuid(), e);
        }

        runtimeService.startProcessInstanceByKey(processKey, properties);
    }
}
