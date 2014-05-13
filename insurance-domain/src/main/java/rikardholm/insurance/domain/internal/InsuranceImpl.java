package rikardholm.insurance.domain.internal;

import rikardholm.insurance.domain.Customer;
import rikardholm.insurance.domain.Insurance;
import rikardholm.insurance.domain.InsuranceNumber;

public class InsuranceImpl implements Insurance {
    private final InsuranceNumber insuranceNumber;
    public final Customer customer;
    private final String address;

    public InsuranceImpl(InsuranceNumber insuranceNumber, Customer customer, String address) {
        this.insuranceNumber = insuranceNumber;
        this.customer = customer;
        this.address = address;
    }

    public InsuranceImpl(InsuranceNumber insuranceNumber, CustomerImpl customer, String address) {
        this(insuranceNumber, (Customer) customer, address);
    }

    @Override
    public Customer getCustomer() {
        return customer;
    }

    @Override
    public InsuranceNumber getInsuranceNumber() {
        return insuranceNumber;
    }

    public String getAddress() {
        return address;
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
