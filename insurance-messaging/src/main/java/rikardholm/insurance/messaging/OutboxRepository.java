package rikardholm.insurance.messaging;

import rikardholm.insurance.messaging.message.OutboxMessage;

public interface OutboxRepository extends MessageRepository<OutboxMessage> {
}
