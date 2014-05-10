package rikardholm.insurance.domain;

import rikardholm.insurance.domain.common.AbstractValueObject;

public class PersonalIdentifier extends AbstractValueObject<String> {

    private PersonalIdentifier(String personalIdentifier) {
        super(personalIdentifier);
    }

    public static PersonalIdentifier of(String personalIdentifier) {
        return new PersonalIdentifier(personalIdentifier);
    }
}
