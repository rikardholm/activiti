package rikardholm.insurance.application.messaging.message;

import rikardholm.insurance.application.messaging.IncomingMessage;
import rikardholm.insurance.domain.customer.PersonalIdentifier;

import static java.util.Objects.requireNonNull;

public class InsuranceInformationRequest implements IncomingMessage {
    public final PersonalIdentifier personalIdentifier;

    public InsuranceInformationRequest(PersonalIdentifier personalIdentifier) {
        this.personalIdentifier = requireNonNull(personalIdentifier);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InsuranceInformationRequest that = (InsuranceInformationRequest) o;

        if (!personalIdentifier.equals(that.personalIdentifier)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return personalIdentifier.hashCode();
    }
}
