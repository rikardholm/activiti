package rikardholm.insurance.transfer;


import org.activiti.engine.HistoryService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import rikardholm.insurance.application.messaging.InboxRepository;
import rikardholm.insurance.application.messaging.MessageEvent;
import rikardholm.insurance.application.messaging.MessageEventRepository;
import rikardholm.insurance.application.messaging.event.MessageHandledEvent;
import rikardholm.insurance.infrastructure.inmemory.InMemoryInboxRepository;
import rikardholm.insurance.application.messaging.IncomingMessage;
import rikardholm.insurance.infrastructure.inmemory.InMemoryMessageEventRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.requireNonNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ProcessDispatcherTest {
    public static final TestMessage INCOMING_MESSAGE = new TestMessage(7);
    public static final String PROCESS_KEY = "noop-process";
    public final InboxRepository inboxRepository = new InMemoryInboxRepository();
    public final MessageEventRepository messageEventRepository = new InMemoryMessageEventRepository();

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule("test/spring/activiti.standalone.inmemory.cfg.xml");

    private ProcessDispatcher processDispatcher;

    @Before
    public void setUp() throws Exception {
        Map<Class<? extends IncomingMessage>, String> processMap = new HashMap<Class<? extends IncomingMessage>, String>();
        processMap.put(TestMessage.class, PROCESS_KEY);

        processDispatcher = new ProcessDispatcher(activitiRule.getRuntimeService(), inboxRepository, messageEventRepository, processMap);
    }

    @Test
    @Deployment(resources = "rikardholm/transfer/workflow/start-stop-process.bpmn")
    public void starts_a_mapped_process() throws Exception {
        inboxRepository.create(INCOMING_MESSAGE);
        processDispatcher.pollInbox();

        HistoricProcessInstance instance = getHistoricProcessInstance();

        assertThat(instance, is(notNullValue()));
    }

    @Test
    @Deployment(resources = "rikardholm/transfer/workflow/start-stop-process.bpmn")
    public void sets_IncomingMessage_as_process_variable() throws Exception {
        inboxRepository.create(INCOMING_MESSAGE);
        processDispatcher.pollInbox();

        HistoricProcessInstance historicProcessInstance = getHistoricProcessInstance();

        TestMessage incomingMessage = (TestMessage) historicProcessInstance.getProcessVariables().get("incomingMessage");

        assertThat(incomingMessage, equalTo(INCOMING_MESSAGE));
    }

    @Test
    @Deployment(resources = "rikardholm/transfer/workflow/start-stop-process.bpmn")
    public void adds_MessageHandledEvent_after_processing() throws Exception {
        inboxRepository.create(INCOMING_MESSAGE);
        processDispatcher.pollInbox();

        List<MessageEvent> messageEvents = messageEventRepository.findBy(INCOMING_MESSAGE);

        assertThat(messageEvents, contains(instanceOf(MessageHandledEvent.class)));
    }

    @Test
    @Deployment(resources = "rikardholm/transfer/workflow/start-stop-process.bpmn")
    public void should_not_process_handled_events() throws Exception {
        inboxRepository.create(INCOMING_MESSAGE);
        messageEventRepository.create(new MessageHandledEvent(INCOMING_MESSAGE));
        processDispatcher.pollInbox();

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

    private static class TestMessage implements IncomingMessage {
        private final int id;

        private TestMessage(int id) {
            this.id = requireNonNull(id);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            TestMessage that = (TestMessage) o;

            if (id != that.id) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return id;
        }
    }
}
