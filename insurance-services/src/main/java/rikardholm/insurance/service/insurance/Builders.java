package rikardholm.insurance.service.insurance;

import rikardholm.insurance.service.insurance.builder.CustomerBuilder;
import rikardholm.insurance.service.insurance.builder.InsuranceBuilder;

public class Builders {
    private Builders() {}

    public static CustomerBuilder.WithPersonalIdentifier aCustomer() {
        return CustomerBuilder.aCustomer();
    }

    public static InsuranceBuilder.WithInsuranceNumber anInsurance() {
        return InsuranceBuilder.anInsurance();
    }
}
