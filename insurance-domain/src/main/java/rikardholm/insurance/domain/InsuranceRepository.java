package rikardholm.insurance.domain;

import com.google.common.base.Optional;
import rikardholm.insurance.domain.common.Repository;

import java.util.List;

public interface InsuranceRepository extends Repository<Insurance> {
    Optional<? extends Insurance> findBy(InsuranceNumber insuranceNumber);
    List<? extends Insurance> findBy(Customer customer);
}
