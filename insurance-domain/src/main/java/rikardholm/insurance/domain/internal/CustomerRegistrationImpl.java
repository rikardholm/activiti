package rikardholm.insurance.domain.internal;

import rikardholm.insurance.domain.customer.CustomerBuilder;
import rikardholm.insurance.domain.customer.*;

public class CustomerRegistrationImpl implements CustomerRegistration {

    private final CustomerRepository customerRepository;

    public CustomerRegistrationImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer register(PersonalIdentifier personalIdentifier, Address address) {
        Customer customer = CustomerBuilder.aCustomer()
                .withPersonalIdentifier(personalIdentifier)
                .withAddress(address)
                .build();

        customerRepository.save(customer);

        return customer;
    }
}
