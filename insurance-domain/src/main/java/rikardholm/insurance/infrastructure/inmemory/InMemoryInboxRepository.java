package rikardholm.insurance.infrastructure.inmemory;


import rikardholm.insurance.application.messaging.InboxRepository;
import rikardholm.insurance.application.messaging.IncomingMessage;

public class InMemoryInboxRepository extends InMemoryMessageRepository<IncomingMessage> implements InboxRepository {
}
