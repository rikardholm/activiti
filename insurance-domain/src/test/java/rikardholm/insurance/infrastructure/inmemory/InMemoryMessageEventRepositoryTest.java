package rikardholm.insurance.infrastructure.inmemory;

import rikardholm.insurance.application.messaging.AbstractMessageEventRepositoryTest;
import rikardholm.insurance.application.messaging.MessageEventRepository;

public class InMemoryMessageEventRepositoryTest extends AbstractMessageEventRepositoryTest {
    @Override
    protected MessageEventRepository getInstance() {
        return new InMemoryMessageEventRepository();
    }
}
