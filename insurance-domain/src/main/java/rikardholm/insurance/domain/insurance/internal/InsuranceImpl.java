package rikardholm.insurance.domain.insurance.internal;

import rikardholm.insurance.domain.insurance.Customer;
import rikardholm.insurance.domain.insurance.Insurance;
import rikardholm.insurance.domain.insurance.InsuranceNumber;

public class InsuranceImpl implements Insurance {
    private final InsuranceNumber insuranceNumber;
    public final Customer customer;

    public InsuranceImpl(InsuranceNumber insuranceNumber, Customer customer) {
        this.insuranceNumber = insuranceNumber;
        this.customer = customer;
    }

    @Override
    public Customer getCustomer() {
        return customer;
    }

    @Override
    public InsuranceNumber getInsuranceNumber() {
        return insuranceNumber;
    }

    @Override
    public String toString() {
        return "Insurance {" + insuranceNumber +
                " of " + customer + "}";
    }
}
