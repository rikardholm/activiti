package rikardholm.insurance.infrastructure.h2;

import org.junit.Rule;
import org.junit.Test;
import rikardholm.insurance.application.messaging.MessageEvent;
import rikardholm.insurance.application.messaging.MessageEventRepository;
import rikardholm.insurance.application.messaging.impl.MessageEventImpl;
import rikardholm.insurance.common.test.database.InMemoryDatabaseRule;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

public class H2MessageEventRepositoryTest {

    public static final String EVENT = "Tested";
    public static final UUID aUUID = java.util.UUID.fromString("587d3880-4e4c-11e4-916c-0800200c9a66");
    @Rule
    public InMemoryDatabaseRule database = new InMemoryDatabaseRule();

    private MessageEventRepository messageEventRepository = new H2MessageEventRepository(database.dataSource);

    @Test
    public void should_append_and_read_a_message_by_UUID() throws Exception {
        MessageEvent messageEvent = new MessageEventImpl(aUUID, EVENT);

        messageEventRepository.append(messageEvent);

        List<MessageEvent> messageEvents = messageEventRepository.findByUUID(aUUID);
        assertThat(messageEvents, hasSize(1));
        assertThat(messageEvents.get(0).getEvent(), equalTo(EVENT));
        assertThat(messageEvents.get(0).getUUID(), equalTo(aUUID));
    }
}