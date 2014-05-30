package rikardholm.insurance.infrastructure.inmemory;

import rikardholm.insurance.domain.customer.AbstractCustomerRepositoryTest;
import rikardholm.insurance.domain.customer.CustomerRepository;

public class InMemoryCustomerRepositoryTest extends AbstractCustomerRepositoryTest {
    @Override
    protected CustomerRepository getInstance() {
        return new InMemoryCustomerRepository();
    }
}
