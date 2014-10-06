package rikardholm.insurance.application.messaging.message;

import rikardholm.insurance.application.messaging.OutgoingMessage;
import rikardholm.insurance.domain.customer.PersonalIdentifier;

import java.time.Instant;
import java.util.UUID;

public class PersonDoesNotExistResponse implements OutgoingMessage {
    public final PersonalIdentifier personalIdentifier;
    public final String ocr;

    public PersonDoesNotExistResponse(PersonalIdentifier personalIdentifier, String ocr) {
        this.personalIdentifier = personalIdentifier;
        this.ocr = ocr;
    }

    @Override
    public UUID getUuid() {
        return null;
    }

    @Override
    public Instant getReceivedAt() {
        return null;
    }

    @Override
    public String getType() {
        return null;
    }

    @Override
    public String getPayload() {
        return null;
    }
}
