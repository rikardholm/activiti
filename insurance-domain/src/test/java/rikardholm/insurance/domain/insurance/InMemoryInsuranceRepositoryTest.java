package rikardholm.insurance.domain.insurance;

import rikardholm.insurance.infrastructure.inmemory.InMemoryInsuranceRepository;

public class InMemoryInsuranceRepositoryTest extends AbstractInsuranceRepositoryTest {
    @Override
    protected InsuranceRepository getInstance() {
        return new InMemoryInsuranceRepository();
    }
}
