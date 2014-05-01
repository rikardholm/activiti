package rikardholm.insurance.service.insurance.internal;

import rikardholm.insurance.service.insurance.AbstractCustomerRepositoryTest;
import rikardholm.insurance.service.insurance.CustomerRepository;

public class InMemoryCustomerRepositoryTest extends AbstractCustomerRepositoryTest {
    @Override
    protected CustomerRepository getInstance() {
        return new InMemoryCustomerRepository();
    }
}
