package rikardholm.insurance.application.messaging.message;

import rikardholm.insurance.application.messaging.OutgoingMessage;

import java.time.Instant;
import java.util.UUID;

public class NoInsurancesResponse implements OutgoingMessage {
    public final String personalIdentificationNumber;

    public NoInsurancesResponse(String personalIdentificationNumber) {
        this.personalIdentificationNumber = personalIdentificationNumber;
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
    public String getPayload() {
        return null;
    }
}
