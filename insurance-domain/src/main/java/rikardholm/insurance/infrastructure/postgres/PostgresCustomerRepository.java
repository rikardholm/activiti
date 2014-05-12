package rikardholm.insurance.infrastructure.postgres;

import com.google.common.base.Optional;
import rikardholm.insurance.domain.PersonalIdentifier;
import rikardholm.insurance.domain.Customer;
import rikardholm.insurance.domain.CustomerRepository;

public class PostgresCustomerRepository implements CustomerRepository {
    private CustomerMapper customerMapper;

    public PostgresCustomerRepository(CustomerMapper customerMapper) {
        this.customerMapper = customerMapper;
    }

    @Override
    public Optional<? extends Customer> findBy(PersonalIdentifier personalIdentifier) {
        return Optional.fromNullable(customerMapper.findByPersonalIdentifier(personalIdentifier));
    }

    @Override
    public void create(Customer instance) {
        PersonalIdentifier personalIdentifier = instance.getPersonalIdentifier();
        Optional<? extends Customer> customer = Optional.fromNullable(customerMapper.findByPersonalIdentifier(personalIdentifier));

        if (customer.isPresent()) {
            throw new IllegalArgumentException("Personal Identifier already exists: " + personalIdentifier);
        }

        customerMapper.insert(instance);
    }

    @Override
    public void delete(Customer instance) {
        customerMapper.delete(instance);
    }


}
