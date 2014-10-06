package rikardholm.insurance.infrastructure.h2;

import org.junit.Rule;
import org.junit.Test;
import rikardholm.insurance.application.messaging.Message;
import rikardholm.insurance.application.messaging.MessageBuilder;
import rikardholm.insurance.application.messaging.MessageRepository2;
import rikardholm.insurance.common.test.database.InMemoryDatabaseRule;

import java.time.Instant;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;

public class H2MessageRepositoryTest {

    @Rule
    public InMemoryDatabaseRule database = new InMemoryDatabaseRule();
    private MessageRepository2 messageRepository = new H2MessageRepository(database.dataSource);

    @Test
    public void appends_and_reads_message() throws Exception {

        Instant instant = Instant.parse("2014-10-04T23:00:00Z");
        Message message = MessageBuilder
                .aMessage()
                .receivedAt(instant)
                .type("test")
                .payload("{\"test\": 123}")
                .build();
        messageRepository.append(message);

        List<Message> messages = messageRepository.receivedAfter(instant);

        assertThat(messages, hasSize(1));
    }

    @Test
    public void returns_messages_in_chronological_order() throws Exception {
        Message a = MessageBuilder
                .aMessage()
                .receivedAt(Instant.parse("2014-10-03T23:00:00Z"))
                .type("a")
                .payload("blabal")
                .build();

        Message b  = MessageBuilder
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

        assertThat(messages, contains(a,b,c));
    }
}