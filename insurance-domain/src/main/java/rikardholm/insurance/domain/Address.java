package rikardholm.insurance.domain;

import rikardholm.insurance.domain.common.AbstractValueObject;

import java.io.Serializable;

public class Address extends AbstractValueObject<String> implements Serializable {
    private Address(String value) {
        super(value);
    }

    public static Address of(String value) {
        return new Address(value);
    }
}
