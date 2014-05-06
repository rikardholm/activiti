package rikardholm.insurance.application.spar;

import rikardholm.insurance.domain.PersonalIdentifier;

public class SparResult {
    public final PersonalIdentifier personalIdentifier;

    private SparResult(PersonalIdentifier personalIdentifier) {
        this.personalIdentifier = personalIdentifier;
    }

    public static Found found(PersonalIdentifier personalIdentifier, String address) {
        return new Found(personalIdentifier, address);
    }

    public static Secret secret(PersonalIdentifier personalIdentifier) {
        return new Secret(personalIdentifier);
    }

    public static class Found extends SparResult {
        public final String address;

        private Found(PersonalIdentifier personalIdentifier, String address) {
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
