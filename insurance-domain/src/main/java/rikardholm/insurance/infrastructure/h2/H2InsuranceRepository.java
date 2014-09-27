package rikardholm.insurance.infrastructure.h2;

import com.google.common.base.Optional;
import rikardholm.insurance.domain.customer.Customer;
import rikardholm.insurance.domain.insurance.Insurance;
import rikardholm.insurance.domain.insurance.InsuranceNumber;
import rikardholm.insurance.domain.insurance.InsuranceRepository;

import java.util.List;

public class H2InsuranceRepository implements InsuranceRepository {
    private final InsuranceMapper insuranceMapper;
    private final CustomerMapper customerMapper;

    public H2InsuranceRepository(InsuranceMapper insuranceMapper, CustomerMapper customerMapper) {
        this.insuranceMapper = insuranceMapper;
        this.customerMapper = customerMapper;
    }

    @Override
    public Optional<? extends Insurance> findBy(InsuranceNumber insuranceNumber) {
        return Optional.fromNullable(insuranceMapper.findByInsuranceNumber(insuranceNumber));
    }

    @Override
    public List<? extends Insurance> findBy(Customer customer) {
        return insuranceMapper.findByCustomer(customer);
    }

    @Override
    public void save(Insurance insurance) {
        if (findBy(insurance.getInsuranceNumber()).isPresent()) {
            throw new IllegalArgumentException("An Insurance with this InsuranceNumber already exists: " + insurance.getInsuranceNumber());
        }

        Integer customerId = customerMapper.selectId(insurance.getCustomer());

        insuranceMapper.insert(insurance, customerId);
    }

    @Override
    public void delete(Insurance instance) {
        insuranceMapper.delete(instance);
    }
}
