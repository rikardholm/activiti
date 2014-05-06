package rikardholm.insurance.transfer;

import rikardholm.insurance.domain.InsuranceNumber;

import java.io.Serializable;

public class InsuranceInformation implements Serializable {
    public final InsuranceNumber insuranceNumber;

    public InsuranceInformation(InsuranceNumber insuranceNumber) {
        this.insuranceNumber = insuranceNumber;
    }
}
