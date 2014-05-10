package rikardholm.insurance.domain.internal;

import rikardholm.insurance.domain.Customer;
import rikardholm.insurance.domain.Insurance;
import rikardholm.insurance.domain.InsuranceNumber;

public class InsuranceImpl implements Insurance {
    private final InsuranceNumber insuranceNumber;
    public final Customer customer;

    public InsuranceImpl(InsuranceNumber insuranceNumber, Customer customer) {
        this.insuranceNumber = insuranceNumber;
        this.customer = customer;
    }

    public InsuranceImpl(InsuranceNumber insuranceNumber, CustomerImpl customer) {
        this(insuranceNumber, (Customer) customer);
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InsuranceImpl insurance = (InsuranceImpl) o;

        if (!insuranceNumber.equals(insurance.insuranceNumber)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return insuranceNumber.hashCode();
    }

    @Override
    public String toString() {
        return "Insurance {" + insuranceNumber +
                " of " + customer + "}";
    }
}
