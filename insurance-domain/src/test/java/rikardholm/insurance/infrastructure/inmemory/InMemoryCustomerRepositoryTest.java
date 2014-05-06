package rikardholm.insurance.infrastructure.inmemory;

import rikardholm.insurance.domain.AbstractCustomerRepositoryTest;
import rikardholm.insurance.domain.CustomerRepository;

public class InMemoryCustomerRepositoryTest extends AbstractCustomerRepositoryTest {
    @Override
    protected CustomerRepository getInstance() {
        return new InMemoryCustomerRepository();
    }
}
