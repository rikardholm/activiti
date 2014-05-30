package rikardholm.insurance.domain.customer;

import rikardholm.insurance.domain.common.AbstractValueObject;

import java.io.Serializable;

public class PersonalIdentifier extends AbstractValueObject<String> implements Serializable {
    private static final long serialVersionUID = -933281690786302175L;

    private PersonalIdentifier(String personalIdentifier) {
        super(personalIdentifier);
    }

    public static PersonalIdentifier of(String personalIdentifier) {
        return new PersonalIdentifier(personalIdentifier);
    }
}
