package rikardholm.insurance.domain.insurance;

import rikardholm.insurance.domain.common.AbstractValueObject;

import java.io.Serializable;

public class InsuranceNumber extends AbstractValueObject<Long> implements Serializable {

    private InsuranceNumber(Long insuranceNumber) {
        super(insuranceNumber);
    }

    public static InsuranceNumber of(Long value) {
        return new InsuranceNumber(value);
    }
}
