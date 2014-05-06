package rikardholm.insurance.service.insurance.internal;

import rikardholm.insurance.service.insurance.Customer;
import rikardholm.insurance.service.insurance.Insurance;
import rikardholm.insurance.service.insurance.InsuranceNumber;

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
