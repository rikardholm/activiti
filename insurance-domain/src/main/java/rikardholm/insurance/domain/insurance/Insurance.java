package rikardholm.insurance.domain.insurance;

import rikardholm.insurance.domain.customer.Customer;

public interface Insurance {
    InsuranceNumber getInsuranceNumber();

    Customer getCustomer();
}
