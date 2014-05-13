package rikardholm.insurance.domain;

public interface Insurance {
    InsuranceNumber getInsuranceNumber();

    Customer getCustomer();

    String getAddress();
}
