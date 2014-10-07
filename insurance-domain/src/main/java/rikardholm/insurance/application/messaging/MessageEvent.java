package rikardholm.insurance.application.messaging;

import java.util.UUID;

public interface MessageEvent {
    @Deprecated
    Message getMessage();

    UUID getUUID();
    String getEvent();
}
