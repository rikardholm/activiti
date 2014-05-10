package rikardholm.insurance.infrastructure.inmemory;

import rikardholm.insurance.domain.AbstractInsuranceRepositoryTest;
import rikardholm.insurance.domain.CustomerRepository;
import rikardholm.insurance.domain.InsuranceRepository;

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
