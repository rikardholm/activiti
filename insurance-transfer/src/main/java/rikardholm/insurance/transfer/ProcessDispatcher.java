package rikardholm.insurance.transfer;

import org.activiti.engine.RuntimeService;
import rikardholm.insurance.messaging.message.IncomingMessage;

import java.util.HashMap;
import java.util.Map;

public class ProcessDispatcher implements MessageHandler.Listener {

    public static final String INCOMING_MESSAGE_PROPERTY = "incomingMessage";
    private RuntimeService runtimeService;
    private final Map<Class<? extends IncomingMessage>, String> processMap;

    public ProcessDispatcher(RuntimeService runtimeService, Map<Class<? extends IncomingMessage>, String> processMap) {
        this.runtimeService = runtimeService;
        this.processMap = processMap;
    }

    @Override
    public void newMessage(IncomingMessage incomingMessage) {
        String processKey = processMap.get(incomingMessage.getClass());

        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put(
                INCOMING_MESSAGE_PROPERTY, incomingMessage);

        runtimeService.startProcessInstanceByKey(processKey, properties);
    }
}
