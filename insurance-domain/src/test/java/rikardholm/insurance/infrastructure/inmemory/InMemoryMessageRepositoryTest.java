package rikardholm.insurance.infrastructure.inmemory;

import org.junit.Test;
import rikardholm.insurance.application.messaging.Message;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

public class InMemoryMessageRepositoryTest {
    private InMemoryMessageRepository<GeneralMessage> target = new InMemoryMessageRepository<GeneralMessage>();

    @Test
    public void find_should_return_all_messages_of_the_given_type() throws Exception {
        AMessage messageOne = new AMessage();
        AMessage messageTwo = new AMessage();
        BMessage bMessage = new BMessage();

        target.save(messageOne);
        target.save(messageTwo);
        target.save(bMessage);

        List<AMessage> aMessages = target.find(AMessage.class);
        List<BMessage> bMessages = target.find(BMessage.class);
        List<GeneralMessage> allMessages = target.find(GeneralMessage.class);

        assertThat(aMessages, contains(messageOne, messageTwo));
        assertThat(bMessages, contains(bMessage));
        assertThat(allMessages, contains(messageOne, messageTwo, bMessage));
    }

    private static class GeneralMessage implements Message {
        @Override
        public UUID getUuid() {
            return null;
        }

        @Override
        public Instant getReceivedAt() {
            return null;
        }

        @Override
        public String getPayload() {
            return null;
        }
    }

    private static class AMessage extends GeneralMessage {}

    private static class BMessage extends GeneralMessage {}
}
