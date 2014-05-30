package rikardholm.insurance.infrastructure.inmemory;

import rikardholm.insurance.domain.insurance.AbstractInsuranceRepositoryTest;
import rikardholm.insurance.domain.customer.CustomerRepository;
import rikardholm.insurance.domain.insurance.InsuranceRepository;

public class InMemoryInsuranceRepositoryTest extends AbstractInsuranceRepositoryTest {
    @Override
    protected InsuranceRepository getInstance() {
        return new InMemoryInsuranceRepository();
    }

    @Override
    protected CustomerRepository getCustomerRepository() {
        return new InMemoryCustomerRepository();
    }
}
