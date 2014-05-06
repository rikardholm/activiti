package rikardholm.insurance.domain.insurance;

import java.io.Serializable;

public class InsuranceNumber implements Serializable {
    private final Long insuranceNumber;

    private InsuranceNumber(Long insuranceNumber) {
        this.insuranceNumber = insuranceNumber;
    }

    public Long getInsuranceNumber() {
        return insuranceNumber;
    }

    @Override
    public String toString() {
        return "Insurance Number " + insuranceNumber;
    }

    public static InsuranceNumber of(Long value) {
        return new InsuranceNumber(value);
    }
}
