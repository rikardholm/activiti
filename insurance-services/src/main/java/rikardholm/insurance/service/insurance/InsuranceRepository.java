package rikardholm.insurance.service.insurance;

import rikardholm.insurance.service.Optional;

import java.util.List;

public interface InsuranceRepository extends Repository<Insurance> {
    Optional<Insurance> findBy(InsuranceNumber insuranceNumber);
    List<Insurance> findBy(Customer customer);
}
