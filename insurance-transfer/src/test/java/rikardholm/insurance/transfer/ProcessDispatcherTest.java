package rikardholm.insurance.transfer;


import org.activiti.engine.HistoryService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import rikardholm.insurance.application.messaging.Message;
import rikardholm.insurance.application.messaging.MessageBuilder;
import rikardholm.insurance.application.messaging.MessageEventRepository;
import rikardholm.insurance.application.messaging.MessageRepository;
import rikardholm.insurance.application.messaging.impl.MessageEventImpl;
import rikardholm.insurance.common.test.database.InMemoryDatabaseRule;
import rikardholm.insurance.infrastructure.h2.H2MessageEventRepository;
import rikardholm.insurance.infrastructure.h2.H2MessageRepository;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ProcessDispatcherTest {
    public static final Message MESSAGE = MessageBuilder
            .aMessage()
            .withUUID(UUID.fromString("23144810-4e41-11e4-916c-0800200c9a66"))
            .receivedAt(Instant.parse("2014-10-07T18:44:00z"))
            .payload("{\"test\": 123}")
            .build();
    public static final String PROCESS_KEY = "noop-process";

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule("test/spring/activiti.standalone.inmemory.cfg.xml");
    @Rule
    public InMemoryDatabaseRule database = new InMemoryDatabaseRule();

    public final MessageRepository messageRepository = new H2MessageRepository(database.dataSource, "inbox");
    public final MessageEventRepository messageEventRepository = new H2MessageEventRepository(database.dataSource);

    private ProcessDispatcher processDispatcher;

    @Before
    public void setUp() throws Exception {
        Map<Class<Message>, String> processMap = new HashMap<Class<Message>, String>();
        processMap.put(Message.class, PROCESS_KEY);

        processDispatcher = new ProcessDispatcher(activitiRule.getRuntimeService(), messageRepository, messageEventRepository, processMap);
    }

    @Test
    @Deployment(resources = "rikardholm/transfer/workflow/start-stop-process.bpmn")
    public void starts_a_mapped_process() throws Exception {
        messageRepository.append(MESSAGE);
        processDispatcher.poll();

        HistoricProcessInstance instance = getHistoricProcessInstance();

        assertThat(instance, is(notNullValue()));
    }

    @Test
    @Deployment(resources = "rikardholm/transfer/workflow/start-stop-process.bpmn")
    public void should_set_process_variables_for_message_and_converted_payload() throws Exception {
        messageRepository.append(MESSAGE);
        processDispatcher.poll();

        HistoricProcessInstance historicProcessInstance = getHistoricProcessInstance();

        Message message = (Message) historicProcessInstance.getProcessVariables().get("message");

        Map<String,Object> payload = (Map<String, Object>) historicProcessInstance.getProcessVariables().get("messagePayload");

        assertThat(message.getUuid(), equalTo(MESSAGE.getUuid()));
        assertThat(message.getPayload(), equalTo(MESSAGE.getPayload()));
        assertThat(payload.get("test"), equalTo(123));
    }

    @Test
    @Deployment(resources = "rikardholm/transfer/workflow/start-stop-process.bpmn")
    public void should_not_process_handled_events() throws Exception {
        messageRepository.append(MESSAGE);
        messageEventRepository.append(new MessageEventImpl(MESSAGE.getUuid(), "asdf"));
        processDispatcher.poll();

        HistoricProcessInstance historicProcessInstance = getHistoricProcessInstance();

        assertThat(historicProcessInstance, is(nullValue()));
    }

    private HistoricProcessInstance getHistoricProcessInstance() {
        HistoryService historyService = activitiRule.getHistoryService();

        return historyService.createHistoricProcessInstanceQuery()
                .processDefinitionKey(PROCESS_KEY)
                .includeProcessVariables()
                .singleResult();
    }
}
