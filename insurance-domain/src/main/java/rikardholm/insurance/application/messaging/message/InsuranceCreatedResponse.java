package rikardholm.insurance.application.messaging.message;

import rikardholm.insurance.application.messaging.OutgoingMessage;
import rikardholm.insurance.domain.customer.PersonalIdentifier;
import rikardholm.insurance.domain.insurance.InsuranceNumber;

public class InsuranceCreatedResponse implements OutgoingMessage {
    public final String ocr;
    public final PersonalIdentifier personalIdentifier;
    public final InsuranceNumber insuranceNumber;

    public InsuranceCreatedResponse(String ocr, PersonalIdentifier personalIdentifier, InsuranceNumber insuranceNumber) {
        this.ocr = ocr;
        this.personalIdentifier = personalIdentifier;
        this.insuranceNumber = insuranceNumber;
    }
}
