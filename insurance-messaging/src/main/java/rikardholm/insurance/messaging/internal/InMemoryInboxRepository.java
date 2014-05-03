package rikardholm.insurance.messaging.internal;

import rikardholm.insurance.messaging.InboxRepository;
import rikardholm.insurance.messaging.message.IncomingMessage;

public class InMemoryInboxRepository extends InMemoryMessageRepository<IncomingMessage> implements InboxRepository {
}
