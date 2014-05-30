package rikardholm.insurance.domain.insurance;

import rikardholm.insurance.domain.customer.Customer;

public interface InsuranceRegistration {
    Insurance register(Customer customer);
}
