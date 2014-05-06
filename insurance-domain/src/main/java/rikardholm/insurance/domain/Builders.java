package rikardholm.insurance.domain;

import rikardholm.insurance.domain.builder.CustomerBuilder;
import rikardholm.insurance.domain.builder.InsuranceBuilder;

public class Builders {
    private Builders() {}

    public static CustomerBuilder.WithPersonalIdentifier aCustomer() {
        return CustomerBuilder.aCustomer();
    }

    public static InsuranceBuilder.WithInsuranceNumber anInsurance() {
        return InsuranceBuilder.anInsurance();
    }
}
