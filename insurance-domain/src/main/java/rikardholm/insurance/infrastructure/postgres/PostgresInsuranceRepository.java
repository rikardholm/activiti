package rikardholm.insurance.infrastructure.postgres;

import rikardholm.insurance.common.Optional;
import rikardholm.insurance.domain.Customer;
import rikardholm.insurance.domain.Insurance;
import rikardholm.insurance.domain.InsuranceNumber;
import rikardholm.insurance.domain.InsuranceRepository;

import java.util.List;

public class PostgresInsuranceRepository implements InsuranceRepository {

    private InsuranceMapper insuranceMapper;

    public PostgresInsuranceRepository(InsuranceMapper insuranceMapper) {
        this.insuranceMapper = insuranceMapper;
    }

    @Override
    public Optional<Insurance> findBy(InsuranceNumber insuranceNumber) {
        return null;
    }

    @Override
    public List<Insurance> findBy(Customer customer) {
        return null;
    }

    @Override
    public void create(Insurance instance) {

    }

    @Override
    public void delete(Insurance instance) {

    }
}
