package rikardholm.insurance.messaging.message;

public class InsuranceInformationRequest implements IncomingMessage {
    public final String personalIdentificationNumber;

    public InsuranceInformationRequest(String personalIdentificationNumber) {
        this.personalIdentificationNumber = personalIdentificationNumber;
    }
}
