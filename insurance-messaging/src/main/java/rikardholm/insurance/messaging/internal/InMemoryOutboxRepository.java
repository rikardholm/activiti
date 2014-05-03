package rikardholm.insurance.messaging.internal;

import rikardholm.insurance.messaging.OutboxRepository;
import rikardholm.insurance.messaging.message.OutgoingMessage;

public class InMemoryOutboxRepository extends InMemoryMessageRepository<OutgoingMessage> implements OutboxRepository {
}
