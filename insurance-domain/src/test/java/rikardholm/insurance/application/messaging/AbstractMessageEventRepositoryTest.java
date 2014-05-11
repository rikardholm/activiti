package rikardholm.insurance.application.messaging;

import org.junit.Test;
import rikardholm.insurance.application.messaging.event.MessageHandledEvent;
import rikardholm.insurance.domain.AbstractContractTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public abstract class AbstractMessageEventRepositoryTest extends AbstractContractTest<MessageEventRepository> {

    public static final Message MESSAGE = new TestMessage();
    public static final MessageEvent MESSAGE_HANDLED_EVENT = new MessageHandledEvent(MESSAGE);

    @Test
    public void should_return_empty_list_when_nothing_is_found() throws Exception {
        List<MessageEvent> result = target.findBy(MESSAGE);

        assertThat(result, is(notNullValue()));
        assertThat(result, is(empty()));
    }

    @Test
    public void should_be_able_to_find_MessageEvent_that_was_added() throws Exception {
        target.create(MESSAGE_HANDLED_EVENT);

        List<MessageEvent> messageEvents = target.findBy(MESSAGE);
        assertThat(messageEvents, contains(MESSAGE_HANDLED_EVENT));
    }

    private static class TestMessage implements Message {
    }
}