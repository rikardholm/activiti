package rikardholm.insurance.infrastructure.oracle;

import java.util.List;

import com.google.common.base.Optional;

import rikardholm.insurance.domain.Customer;
import rikardholm.insurance.domain.Insurance;
import rikardholm.insurance.domain.InsuranceNumber;
import rikardholm.insurance.domain.InsuranceRepository;

public class OracleInsuranceRepository implements InsuranceRepository {

	private final InsuranceMapper insuranceMapper;
	private final CustomerMapper customerMapper;

	public OracleInsuranceRepository(InsuranceMapper insuranceMapper, CustomerMapper customerMapper) {
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
	public void create(Insurance instance) {
		Integer customerId = customerMapper.selectId(instance.getCustomer());

		insuranceMapper.insert(instance, customerId);
	}

	@Override
	public void delete(Insurance instance) {
		insuranceMapper.delete(instance);
	}
}
