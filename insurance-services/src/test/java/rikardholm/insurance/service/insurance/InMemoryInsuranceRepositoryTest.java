package rikardholm.insurance.service.insurance;

import rikardholm.insurance.service.insurance.internal.InMemoryInsuranceRepository;

public class InMemoryInsuranceRepositoryTest extends AbstractInsuranceRepositoryTest {
    @Override
    protected InsuranceRepository getInstance() {
        return new InMemoryInsuranceRepository();
    }
}
