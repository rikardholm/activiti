package rikardholm.insurance.application.messaging.message;

import rikardholm.insurance.application.messaging.IncomingMessage;

import static java.util.Objects.requireNonNull;

public class InsuranceInformationRequest implements IncomingMessage {
    public final String personalIdentificationNumber;

    public InsuranceInformationRequest(String personalIdentificationNumber) {
        this.personalIdentificationNumber = requireNonNull(personalIdentificationNumber);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InsuranceInformationRequest that = (InsuranceInformationRequest) o;

        if (!personalIdentificationNumber.equals(that.personalIdentificationNumber)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return personalIdentificationNumber.hashCode();
    }
}
