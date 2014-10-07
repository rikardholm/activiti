package rikardholm.insurance.infrastructure.h2;

import org.junit.Rule;
import org.junit.Test;
import rikardholm.insurance.application.messaging.Message;
import rikardholm.insurance.application.messaging.MessageBuilder;
import rikardholm.insurance.application.messaging.MessageRepository2;
import rikardholm.insurance.common.test.database.InMemoryDatabaseRule;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertEquals;

public class H2MessageRepositoryTest {

    public static final UUID aUUID = UUID.fromString("1ba943c0-4db3-11e4-916c-0800200c9a66");
    public static final Instant INSTANT = Instant.parse("2014-10-04T23:00:00Z");
    public static final String TYPE = "some type";
    public static final String PAYLOAD = "{\"test\": 123}";

    @Rule
    public InMemoryDatabaseRule database = new InMemoryDatabaseRule();
    private MessageRepository2 messageRepository = new H2MessageRepository(database.dataSource);

    @Test
    public void should_store_values_of_message() throws Exception {
        Message message = MessageBuilder.aMessage()
                .withUUID(aUUID)
                .receivedAt(INSTANT)
                .type(TYPE)
                .payload(PAYLOAD)
                .build();

        messageRepository.append(message);

        Message storedMessage = messageRepository.receivedAfter(INSTANT.minusSeconds(5)).get(0);

        assertEquals(message.getUuid(), storedMessage.getUuid());
        assertEquals(message.getReceivedAt(), storedMessage.getReceivedAt());
        assertEquals(message.getType(), storedMessage.getType());
        assertEquals(message.getPayload(), storedMessage.getPayload());
    }

    @Test
    public void should_return_messages_in_chronological_order() throws Exception {
        Message a = MessageBuilder
                .aMessage()
                .receivedAt(Instant.parse("2014-10-03T23:00:00Z"))
                .type("a")
                .payload("blabal")
                .build();

        Message b = MessageBuilder
                .aMessage()
                .receivedAt(Instant.parse("2014-10-04T23:00:00Z"))
                .type("b")
                .payload("asdf")
                .build();

        Message c = MessageBuilder
                .aMessage()
                .receivedAt(Instant.parse("2014-10-04T23:00:01Z"))
                .type("c")
                .payload("dfgfg")
                .build();

        messageRepository.append(b);
        messageRepository.append(c);
        messageRepository.append(a);

        List<Message> messages = messageRepository.receivedAfter(Instant.parse("2014-10-03T22:00:00Z"));

        assertThat(messages, contains(a, b, c));
    }

    @Test
    public void receivedAfter_should_return_messages_at_same_instant_in_any_order() throws Exception {
        Message a = MessageBuilder
                .aMessage()
                .receivedAt(INSTANT)
                .type("a")
                .payload("blabal")
                .build();

        Message b = MessageBuilder
                .aMessage()
                .receivedAt(INSTANT)
                .type("b")
                .payload("asdf")
                .build();

        Message c = MessageBuilder
                .aMessage()
                .receivedAt(INSTANT)
                .type("c")
                .payload("dfgfg")
                .build();

        messageRepository.append(b);
        messageRepository.append(c);
        messageRepository.append(a);

        List<Message> messages = messageRepository.receivedAfter(INSTANT);

        assertThat(messages, containsInAnyOrder(a, b, c));
    }
}