package rikardholm.insurance.application.messaging.message;

import rikardholm.insurance.application.messaging.OutgoingMessage;
import rikardholm.insurance.domain.customer.PersonalIdentifier;

public class PersonDoesNotExistResponse implements OutgoingMessage {
    public final PersonalIdentifier personalIdentifier;
    public final String ocr;

    public PersonDoesNotExistResponse(PersonalIdentifier personalIdentifier, String ocr) {
        this.personalIdentifier = personalIdentifier;
        this.ocr = ocr;
    }
}
