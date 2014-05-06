package rikardholm.insurance.infrastructure.inmemory;

import rikardholm.insurance.application.messaging.OutboxRepository;
import rikardholm.insurance.application.messaging.OutgoingMessage;

public class InMemoryOutboxRepository extends InMemoryMessageRepository<OutgoingMessage> implements OutboxRepository {
}
