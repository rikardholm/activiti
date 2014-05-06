package rikardholm.insurance.domain.insurance;

import rikardholm.insurance.common.Optional;

import java.util.List;

public interface InsuranceRepository extends Repository<Insurance> {
    Optional<Insurance> findBy(InsuranceNumber insuranceNumber);
    List<Insurance> findBy(Customer customer);
}
