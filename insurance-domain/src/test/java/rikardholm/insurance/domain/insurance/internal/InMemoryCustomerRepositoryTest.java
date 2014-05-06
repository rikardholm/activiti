package rikardholm.insurance.domain.insurance.internal;

import rikardholm.insurance.domain.insurance.AbstractCustomerRepositoryTest;
import rikardholm.insurance.domain.insurance.CustomerRepository;
import rikardholm.insurance.infrastructure.inmemory.InMemoryCustomerRepository;

public class InMemoryCustomerRepositoryTest extends AbstractCustomerRepositoryTest {
    @Override
    protected CustomerRepository getInstance() {
        return new InMemoryCustomerRepository();
    }
}
