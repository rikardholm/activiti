package rikardholm.insurance.application.messaging.message;

import rikardholm.insurance.application.messaging.OutgoingMessage;

import java.util.List;

public class InsuranceInformationResponse implements OutgoingMessage {
    public final String personalIdentificationNumber;
    public final List<Long> insuranceNumbers;

    public InsuranceInformationResponse(String personalIdentificationNumber, List<Long> insuranceNumbers) {
        this.personalIdentificationNumber = personalIdentificationNumber;

        this.insuranceNumbers = insuranceNumbers;
    }


}
