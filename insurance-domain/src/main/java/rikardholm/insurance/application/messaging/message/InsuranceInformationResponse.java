package rikardholm.insurance.application.messaging.message;

import rikardholm.insurance.application.messaging.OutgoingMessage;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class InsuranceInformationResponse implements OutgoingMessage {
    public final String personalIdentificationNumber;
    public final List<Long> insuranceNumbers;

    public InsuranceInformationResponse(String personalIdentificationNumber, List<Long> insuranceNumbers) {
        this.personalIdentificationNumber = personalIdentificationNumber;

        this.insuranceNumbers = insuranceNumbers;
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
