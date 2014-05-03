package rikardholm.insurance.messaging;

import rikardholm.insurance.messaging.message.OutgoingMessage;

public interface OutboxRepository extends MessageRepository<OutgoingMessage> {
}
