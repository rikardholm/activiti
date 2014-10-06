package rikardholm.insurance.infrastructure.h2;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import rikardholm.insurance.application.messaging.Message;
import rikardholm.insurance.application.messaging.MessageBuilder;
import rikardholm.insurance.application.messaging.MessageRepository2;
import rikardholm.insurance.common.test.database.InMemoryDatabaseRule;

import java.time.Instant;
import java.util.List;
import java.util.SortedSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

public class H2MessageRepositoryTest {

    @Rule
    public InMemoryDatabaseRule database = new InMemoryDatabaseRule();

    @Test
    public void appends_and_reads_message() throws Exception {
        MessageRepository2 messageRepository2 = new H2MessageRepository(database.dataSource);

        Instant instant = Instant.parse("2014-10-04T23:00:00Z");
        Message message = MessageBuilder
                .aMessage()
                .receivedAt(instant)
                .build();
        messageRepository2.append(message);

        List<Message> messages = messageRepository2.receivedAfter(instant);

        assertThat(messages, hasSize(1));
    }
}