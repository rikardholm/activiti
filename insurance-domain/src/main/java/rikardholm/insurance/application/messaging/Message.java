package rikardholm.insurance.application.messaging;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

public interface Message extends Serializable {
    UUID getUuid();
    Instant getReceivedAt();
    String getPayload();
}
