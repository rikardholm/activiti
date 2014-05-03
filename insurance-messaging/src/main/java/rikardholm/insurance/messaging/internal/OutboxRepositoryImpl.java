package rikardholm.insurance.messaging.internal;

import rikardholm.insurance.messaging.OutboxRepository;
import rikardholm.insurance.messaging.message.OutboxMessage;

public class OutboxRepositoryImpl extends MessageRepositoryImpl<OutboxMessage> implements OutboxRepository {
}
