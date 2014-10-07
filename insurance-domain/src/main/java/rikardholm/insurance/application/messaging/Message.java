package rikardholm.insurance.application.messaging;

import java.time.Instant;
import java.util.UUID;

public interface Message {
    UUID getUuid();
    Instant getReceivedAt();

    String getPayload();
}
