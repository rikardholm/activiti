package rikardholm.insurance.infrastructure.postgres;

import rikardholm.insurance.common.Optional;
import rikardholm.insurance.domain.PersonalIdentifier;
import rikardholm.insurance.domain.insurance.Customer;
import rikardholm.insurance.domain.insurance.CustomerRepository;

public class PostgresCustomerRepository implements CustomerRepository {
    @Override
    public Optional<Customer> findBy(PersonalIdentifier personalIdentifier) {
        return null;
    }

    @Override
    public void create(Customer instance) {

    }
}
