package rikardholm.insurance.application.spar;

import rikardholm.insurance.domain.customer.Address;
import rikardholm.insurance.domain.customer.PersonalIdentifier;

public class SparResult {
    public final PersonalIdentifier personalIdentifier;

    private SparResult(PersonalIdentifier personalIdentifier) {
        this.personalIdentifier = personalIdentifier;
    }

    public static Found found(PersonalIdentifier personalIdentifier, Address address) {
        return new Found(personalIdentifier, address);
    }

    public static Secret secret(PersonalIdentifier personalIdentifier) {
        return new Secret(personalIdentifier);
    }

    public static class Found extends SparResult {
        public final Address address;

        private Found(PersonalIdentifier personalIdentifier, Address address) {
            super(personalIdentifier);
            this.address = address;
        }
    }

    public static class Secret extends SparResult {
        private Secret(PersonalIdentifier personalIdentifier) {
            super(personalIdentifier);
        }
    }
}
