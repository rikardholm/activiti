package rikardholm.insurance.infrastructure.postgres;

import com.google.common.base.Optional;
import rikardholm.insurance.domain.customer.Customer;
import rikardholm.insurance.domain.insurance.Insurance;
import rikardholm.insurance.domain.insurance.InsuranceNumber;
import rikardholm.insurance.domain.insurance.InsuranceRepository;

import java.util.List;

public class PostgresInsuranceRepository implements InsuranceRepository {

    private InsuranceMapper insuranceMapper;
    private CustomerMapper customerMapper;

    public PostgresInsuranceRepository(InsuranceMapper insuranceMapper, CustomerMapper customerMapper) {
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
    public void save(Insurance instance) {
        Integer customerId = customerMapper.selectId(instance.getCustomer());

        insuranceMapper.insert(instance, customerId);
    }

    @Override
    public void delete(Insurance instance) {
        insuranceMapper.delete(instance);
    }
}
