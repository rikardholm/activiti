package rikardholm.insurance.application.messaging.message;

import rikardholm.insurance.application.messaging.OutgoingMessage;

public class NoInsurancesResponse implements OutgoingMessage {
    public final String personalIdentificationNumber;

    public NoInsurancesResponse(String personalIdentificationNumber) {
        this.personalIdentificationNumber = personalIdentificationNumber;
    }
}
