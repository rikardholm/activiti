package rikardholm.insurance.domain.insurance;

import rikardholm.insurance.domain.insurance.builder.CustomerBuilder;
import rikardholm.insurance.domain.insurance.builder.InsuranceBuilder;

public class Builders {
    private Builders() {}

    public static CustomerBuilder.WithPersonalIdentifier aCustomer() {
        return CustomerBuilder.aCustomer();
    }

    public static InsuranceBuilder.WithInsuranceNumber anInsurance() {
        return InsuranceBuilder.anInsurance();
    }
}
